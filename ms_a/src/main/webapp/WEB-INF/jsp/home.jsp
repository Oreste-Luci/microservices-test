<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>WebService A Home Page</title>

    <!-- Bootstrap -->
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">

</head>
    <body>

        <div class="container">

            <div class="page-header">
                <h1>Microservice A <small>Home Page</small></h1>
            </div>

            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Available Endpoints</div>
                <ul class="list-group">
                    <li class="list-group-item"><a href="/clientmetrics">Client Metrics</a></li>
                    <li class="list-group-item"><a href="/networkmetrics">Network Metrics</a></li>
                    <li class="list-group-item"><a href="/networkprotosmetrics">Network protos Metrics</a></li>
                </ul>
            </div>

        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

    </body>
</html>