<?php
class DB_Connect {
    private $conn;

    // Connecting to database
    public function connect() {
        require_once 'include/Config.php';

        // Connecting to mysql database
        $this->conn = new mysqli(Host, USER, PASS, SCHEMA);

        // return database object
        return $this->conn;
    }
}

?>