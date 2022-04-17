const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));


const mc = mysql.createConnection({
    host: 'mysql.cs.iastate.edu',
    user: 'dbu309srb1',
    password: '4TqVz2qS',
    database: 'db309srb1'
});

mc.connect();

// default route
app.get('/', function (req, res) {
    return res.send({ error: true, message: 'hello' })
});

app.get('/todos', function (req, res) {
    mc.query('SELECT * FROM tasks', function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'Todos list.' });
    });
});

app.get('/todo/:id', function (req, res) {

    let task_id = req.params.id;

    if (!task_id) {
        return res.status(400).send({ error: true, message: 'Please provide task_id' });
    }

    mc.query('SELECT * FROM tasks where id=?', task_id, function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results[0], message: 'Todos list.' });
    });

});

app.get('/todos/search/:keyword', function (req, res) {
    let keyword = req.params.keyword;
    mc.query("SELECT * FROM tasks WHERE task LIKE ? ", ['%' + keyword + '%'], function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'Todos search list.' });
    });
});

app.post('/todo', function (req, res) {

    let task = req.body.task;

    if (!task) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
    }

    mc.query("INSERT INTO tasks SET ? ", { task: task }, function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'New task has been created successfully.' });
    });
});


// port must be set to 8080 because incoming http requests are routed from port 80 to port 8080
app.listen(8080, function () {
    console.log('Node app is running on port 8080');
});
