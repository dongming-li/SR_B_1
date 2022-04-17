<?php
class DB_Connect {
    private $conn;

    // Connecting to database
    public function connect() {
        require_once 'config.php';

        // Connecting to mysql database
        $this->conn = new mysqli(HOST, USER, PASS, SCHEMA);

        // return database object
        return $this->conn;
    }
}

?>