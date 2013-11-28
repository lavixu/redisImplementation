===================
ExoRedis
===================

Welcome to ExoRedis. It is a Redis like in memory storage solution for fast access of data.
ExoRedis implements the get, set, zadd, zrange, zcount, zcard, save, setbit, getbit commands available [here](redis.io/commands).

===============================
Start The Server
================================
At root dir:

To compile:
       
        ant clean compile run

To launch the server:

        //File path is the complete path of the file.
        //This is the path where the data is dumped on exit and loaded on bootup.
        ant run -Darg0=<file_path> 

To launch client:

        //The server is configured to listen for request at 15000 ( not modifiable ).
        telnet host 15000       
 
To terminate client use ctrl-c or exit.

The response is as per redis protocol as mentioned [here](http://redis.io/topics/protocol)

===============
Examples:
================

        set Hello World
        +OK

        get Hello
        $5
        World
        
        setbit bitref 7 1
        :0

        getbit bitref 7
        :1

        zadd key member1 10
        :1
  
        zadd key member2 1 
        :1

       zcard key
        :2
        
       zrange key 0 1
       *2
       $7
       member2
       $7
       member1

