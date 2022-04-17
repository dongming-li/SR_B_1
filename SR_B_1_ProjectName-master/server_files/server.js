// import { connect } from 'net';

const express = require('express'); //Must install express module
const app = express();
const bodyParser = require('body-parser'); //Must install body-parser module
const mysql = require('mysql'); //Must install mysql module
const crypto = require('crypto'); //Must install crypto module
const random_string = require('randomstring');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

/**
 * This is object that we create to use as the connection to make MySQL queries.
 */
const connection = mysql.createConnection({
  host: 'mysql.cs.iastate.edu',
  user: 'dbu309srb1',
  password: '4TqVz2qS',
  database: 'db309srb1'
});

connection.connect();

/**
 * This function makes a query to the database to check to see if the user exist based on the 
 * passed in username
 * @param {String} name - Username of the user you are searching for.
 * @param {Function} callback - Function passed in to handle result
 */
function userExists(name, callback) {
  connection.query('SELECT * FROM user_accounts WHERE username=?', name, function(error, results, fields) {
    if(error){
      console.log(error);
      throw error;
    }
    else if(results[0] == undefined){
      console.log("Im returning false");
      callback(false, null);
    }
    else{
      console.log("Im returning true");
      callback(true, results);
    }
  });
}

/**
 * This function makes a query to the database to check to see if the user exist based on the 
 * passed in email address. 
 * @param {String} email - Email of the user you are searching for.
 * @param {Function} callback - Function passed in to handle result
 */
function emailExists(email, callback) {
  connection.query('SELECT * FROM user_accounts WHERE email=?', email, function(error, results, fields) {
    if(error){
      console.log(error);
      throw error;
    }
    else if(results[0] == undefined){
      console.log("Im returning false");
      callback(false, null);
    }
    else{
      console.log("Im returning true");
      callback(true, results);
    }
  });
}

/**
 * This function is used to encrypt a users password upon account creation so that we do not have plaintext passwords
 * stored in the database. The encryption type is 'AES-256-CBC'.
 * @param {*} password - The password that the user wants to used to log into their account.
 * @param {*} key - This is a key that is generated at the time of account creation that is used to encrypt the password.
 */
function encryptPass(password, key) {
  var cipher = crypto.createCipher('aes-256-cbc', key);
  var encrypted = cipher.update(password, 'utf8', 'hex');
  encrypted += cipher.final('hex');
  return encrypted;
}

/**
 * GET Request
 * This is the default route for the api that will return a simple error message.
 */
app.get('/', function(request, response) {
  return response.send({error: true, message: "The cake is a lie."})
});

/**
 * GET Request
 * This is the api call that will return a list of all the users. It returns them as a 
 */
app.get('/users', function(request, response) {
  connection.query('SELECT * FROM user_accounts', function(error, results) {
    if(error){
      console.log(error);
      throw error;
    }
    return response.send({error: false, data: results, message: 'Users.'});
  });
});

/**
 * GET Request
 * This is the api call that will return a single users account based on the passed in username.
 */
app.get('/user/username/:username', function(request, response) {
  let username = request.params.username;

  if(!username) {
    return response.status(400).send({error: true, message: "Please provide username"});
  }
  userExists(username, function(exist, results){
    if(exist == true){
      response.send({error: false, data: results[0], message: "User found"});
    }
    else if(exist == false){
      response.send({error: true, results: "none", message: "User does not exist."});
    }
  });
});

/**
 * GET Request 
 * This is the api call that is used to fetch a users profile for their profile page.
 */
app.get('/user/profile/:username', function(request, response) {
  let username = request.params.username;

  connection.query('SELECT * FROM user_accounts WHERE username = ?', username, function(error, results){
    if(error){
      console.log(error);
      throw error;
    }
    else{
      response.send({error: false, data: results, message: 'User profile'});
    }
  });
});

/**
 * GET Request
 * This is the api call that will return a single users account based on teh passed in email address.
 */
app.get('/user/email/:email', function(request, response) {
  let email = request.params.email;

  if(!email) {
    return response.status(400).send({error: true, message: "Please provide email"});
  }
  emailExists(email, function(exist, results){
    if(exist == true){
      response.send({error: false, data: results, message: "User found"});
    }
    else if(exist == false){
      response.send({error: true, results: "none", message: "User does not exist."});
    }
  });
});

/**
 * POST Request 
 * This is the api call that is used to create an account. Before the user account is created the request
 * checks to make sure that the username or email is not in use. If they are not then the desired username and email
 * are used to create the account and the password is hashed to avoid plaintext passwords in the database.
 */
app.post('/user', function(request, response) {
  let username = request.body.username;
  let password = request.body.password;
  let email = request.body.email;
  var key = random_string.generate({
  length: 16,
  charset: 'alphanumeric'
});

  if(!username) {
    return response.status(400).send({error: true, message: "Please provide username"});
  }
  userExists(username, function(exist, results){
    if(exist == true){
      response.send({error: true, data: 'none', message: "Username already in use"});
    }
    else if(exist == false){
      emailExists(email, function(exist2, results){
        if(exist2 == true){
          response.send({error: true, data: 'none', message: "Email already in use"});
        }
        else if(exist2 == false) {
          let encrypt_pass = encryptPass(password, key);
          let post = {username: username, password: encrypt_pass, email: email, salt: key};

          connection.query('INSERT INTO user_accounts SET ?', post, function (error, results) {
            if(error){
              console.log(error);
              throw error;
            }
            else{
              console.log(results)
              return response.send({error: false, data: results, message: 'New user has been created'});
            }
          });
        }
      });
    }
  });
});

/**
 * POST Request
 * This is the api call a user makes to attempt to login to their account on the app using their password and 
 * username.
 */
app.post('/user/login/username', function(request, response){
  let username = request.body.username;
  let password = request.body.password;

  if(!username){
    return response.status(400).send({error: true, message: "Please provide username"});
  }
  if(!password){
    return response.status(400).send({error: true, message: "Please provide password"});
  }
  console.log("hello");
  userExists(username, function(exist, results){
    if(exist == true){
      
      console.log(username);
      console.log(password);
      let pass_check = encryptPass(password, results[0]['salt']);
      if(pass_check == results[0]['password']){
        if(username == results[0]['username']){
          return response.send({error: false, data: results[0], message: "Login sucessful"});
        }
        return response.send({error: true, message: "Login failed", cause: "Incorrect Username"});
      }
      else{
        return response.send({error: true, message: "Login failed", cause: "Incorrect Password"});
      }
    }
    else if (exist == false) {
      return response.send({error: true, message: "Incorrect Username", cause: "User does not exist"});
    }
  });
});

/**
 * POST Request
 * This is the api call a user makes to attempt to login to their account on the app using their password and 
 * email.
 */
app.post('/user/login/email', function(request, response){
  let email = request.body.email;
  let password = request.body.password;

  if(!email){
    return response.status(400).send({error: true, message: "Please provide email"});
  }
  if(!password){
    return response.status(400).send({error: true, message: "Please provide password"});
  }
  console.log(email);
  console.log(password);
  emailExists(email, function(exist, results){
    if(exist == true){
      let pass_check = encryptPass(password, results[0]['salt']);
      if(pass_check == results[0]['password']){
        if(email == results[0]['email']){
          return response.send({error: false, data: results[0], message: "Login sucessful", username: results[0]['username']});
        }
        return response.send({error: true, message: "Login failed", cause: "Incorrect email"});
      }
      else{
        return response.send({error: true, message: "Login failed", cause: "Incorrect Password"});
      }
    }
    else if (exist == false) {
      return response.send({error: true, message: "Incorrect email", cause: "Email does not exist"});
    }
  });
});

/**
 * POST Request
 * This is the api call the client makes to create a new game. The game client passes in two usernames which are then used
 * to populate the tables. The request  itself generates a unique game id by getting the current Epoch time and converting it into seconds. 
 */
app.post('/game/create', function(request, response) {
  let player1 = request.body.player1;
  let player2 = request.body.player2;
  let gameId = Math.round((new Date()).getTime() / 1000);
  console.log(typeof(gameId));
  let post = {player1: player1, player2: player2, game: gameId};

  connection.query('INSERT INTO game_instances SET ?', post, function(error, results){
    if(error){
      console.log(error);
      throw error;
    }
    else{
      return response.send({error: false, data: results, message: 'New game has been created', id: gameId});
    }
  });
  connection.query('INSERT INTO game_history SET ?', {id: gameId, history: ""}, function(error, results){
    if(error){
      console.log(error);
      throw error;
    }
    else{
      return;
    }
  });
});

/**
 * GET Request
 * This api call is used to get all of the games currently in the database. It only gets games that are still active. Games that have ended will not show up
 * in the response data.
 */
app.get('/games', function(request, response) {
  connection.query('SELECT * FROM game_instances', function(error, results) {
    if(error){
      console.log(error);
      throw error;
    }
    return response.send({error: false, data: results, message: 'Games'});
  });
});

/**
 * GET Request
 * This api call is used to get all of the multiplayer games that the targeted user is currently in. The response data does not include any games the user
 * is currently in with the AI, or games that have ended.
 */
app.get('/games/user/:username', function(request, response){
  let username = request.params.username;

  connection.query('SELECT * FROM game_instances WHERE (player1 = ? OR player2 = ?) AND NOT player2 = ?', [username, username, 'AI'], function(error, results){
    if(error){
      console.log(error);
      throw error;
    }
    else {
      if(results == 0){
        return response.send({error: true, message: "No games" });
      }
      else{
        let player = "";
        if(results[0]['player1'] == username){
          player = 'player1';
        }
        else{
          player = 'player2';
        }
        return response.send({error: false, data: results, player: player, gameID: results[0]['game'], message: 'Games'});
      }
    }
  });
});

/**
 * GET Request
 * This api call is used to get the board state of the specified game ID. The format of the returned game state is a string.
 */
app.get('/game/board/:gameID', function(request, response){
  let gameID = request.params.gameID;

  connection.query('SELECT * FROM game_history WHERE id = ?', gameID, function(error, result){
    if(error){
      console.log(error);
      throw error;
    }
    else {
      console.log(result[0]['history']);
      return response.send({error: false, data: result, message: 'Gameboard', history: result[0]['history']});
    }
  });
});

/**
 * POST Request
 * This api call is used to update the game state for a specified game. The user specifies a move in the form of a string and 
 * the ID of the game. The database is then updated accordingly.
 */
app.post('/game/board/update', function(request, response) {
  let move = request.body.move;
  let gameID = request.body.gameID

  console.log(move);
  connection.query('UPDATE game_history SET history = CONCAT(history, ?)  WHERE id = ?', [move, gameID], function(error, results){
    if(error){
      console.log(error)
      throw error;
    }
    else {
      return response.send({error: false, data: results, messgae: 'Move sent'});
    }
  });
});

/**
 * POST Request
 * This api call is used to make a chat between two player accounts. The method recieves two account usernames and then 
 * creates a chat between them. The chatID is generated at the time of the method call by converting the Epoch time to seconds.
 */
app.post('/chat/create', function(request, response) {
  let user1 = request.body.user1;
  let user2 = request.body.user2;
  let chatID = Math.round((new Date()).getTime() / 1000);
  let post = {user1: user1, user2: user2, chatID: chatID};

  connection.query('INSERT INTO chat_instances SET ?', post, function(error, results){
    if(error){
      console.log(error);
      throw error;
    }
    else{
      return response.send({error: false, data: results, message: 'New chat has been created'});
    }
  });
    connection.query('INSERT INTO chat_logs SET ?', {chatID: chatID, chat_log: ""}, function(error, results){
      if(error){
        console.log(error);
        throw error;
      }
      else{
        return;
      }
    });
});

/**
 * GET Request 
 * This api method returns all of the chats that the passed in user is currently in. Chats that are no longer in use are not included in this call.
 */
app.get('/chats/user/:username', function(request, response){
  let username = request.params.username;

  connection.query('SELECT * FROM chat_instances WHERE user1 = ? OR user2 = ?', [username, username], function(error, results){
    if(error){
      console.log(error);
      throw error;
    }
    else {
      return response.send({error: false, data: results, message: 'Chats'});
    }
  });
});

/**
 * POST Request
 * This api method sends a message to the designated chat. The user passes in a message value in the form of a string and the ID of the chat.
 */
app.post('/chat/send', function(request, response){
  let chatID = request.body.chatID;
  let msg = request.body.msg;

  console.log(msg);
  connection.query('UPDATE chat_logs SET chat_log = CONCAT(chat_log, ?, "\n")  WHERE chatID = ?', [msg, chatID], function(error, results){
    if(error){
      console.log(error)
      throw error;
    }
    else {
      return response.send({error: false, data: results, message: 'Message sent'});
    }
  });
});

/**
 * GET Request
 * This api request returns all of the messages that have been sent in a specified chat.
 */
app.get('/chat/logs/:chatID', function(request, response){
  let chatID = request.params.chatID;

  connection.query('SELECT chat_log FROM chat_logs WHERE chatID = ?', chatID, function(error, result){
    if(error){
      console.log(error);
      throw error;
    }
    else {
      return response.send({error: false, data: result, message: 'Chat'});
    }
  });
});

/**
 * POST Request
 * This api request will update the users' profiles at the end of the game. It also deletes the game instance frome the table in the 
 * database so it can no longer be access by either player.
 */
app.post('/game/end', function(request, response){
  let winner = request.body.winner;
  let loser = request.body.loser;
  let gameID = request.body.gameID;
  let draw = request.body.draw;

  if(!draw){
    connection.query('UPDATE user_accounts SET wins = wins + 1 WHERE username = ?', winner, function(error, result){
      if(error){
        console.log(error);
        throw error;
      }
      else{
        return response.send({error: false, data: result, message: "Winner updated"});
      }
    });
    connection.query('UPDATE user_accounts SET win_loss_ratio = (wins / losses) WHERE username = ?', winner, function (error, result) {
      if (error) {
        console.log(error);
        throw error;
      }
      else {
        return;
      }
    });
    connection.query('UPDATE user_accounts SET losses = losses + 1 WHERE username = ?', loser, function (error, result) {
      if (error) {
        console.log(error);
        throw error;
      }
      else {
        return;
      }
    });
    connection.query('UPDATE user_accounts SET win_loss_ratio = (wins / losses) WHERE username = ?', loser, function (error, result) {
      if (error) {
        console.log(error);
        throw error;
      }
      else {
        return;
      }
    });
  }
  else{
    connection.query('UPDATE user_accounts SET draws = draws + 1 WHERE username = ? OR username = ?', winner, loser, function(error, result){
      if(error){
        console.log(error);
        throw error;
      }
      else{
        return response.send({error: false, data: result, message: "Players updated"});
      }
    });
  }
  connection.query('DELETE FROM game_instances WHERE game = ?', gameID, function(error, result){
    if(error){
      console.log(error);
      throw error;
    }
    else{
      return;
    }
  });
});

/**
 * GET Request
 * This api call returns an array of all the AI games that a user is currently in.
 */
app.get('/games/AI/:username', function(request, response){
  let username = request.params.username;

  connection.query('SELECT * FROM game_instances WHERE player1 = ? AND player2 = ?', [username, 'AI'], function (error, results) {
    if (error) {
      console.log(error);
      throw error;
    }
    else {
      if(results == 0){
        return response.send({error: true, message: "No games" });
      }
      else{
        return response.send({ error: false, data: results, gameID: results[0]['game'], message: 'Games' });
      }
    }
  });
});

/**
 * GET Request
 * This api call creates a game with the AI based on the passed in username.
 */
app.get('/game/AI/create/:username', function (request, response) {
  let player1 = request.params.username;
  let player2 = 'AI';
  let gameId = Math.round((new Date()).getTime() / 1000);
  console.log(typeof (gameId));
  let post = { player1: player1, player2: player2, game: gameId };

  connection.query('INSERT INTO game_instances SET ?', post, function (error, results) {
    if (error) {
      console.log(error);
      throw error;
    }
    else {
      return response.send({ error: false, data: results, message: 'New game has been created', id: gameId });
    }
  });
  connection.query('INSERT INTO game_history SET ?', { id: gameId, history: "" }, function (error, results) {
    if (error) {
      console.log(error);
      throw error;
    }
    else {
      return;
    }
  });
});

/**
 * This function simply tells the server which port to listen on.
 */
app.listen(8008, function () {
    console.log('Node app is running on port 8008');
});