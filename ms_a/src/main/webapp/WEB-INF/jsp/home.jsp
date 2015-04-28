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
                <div class="panel-heading">Available URLs</div>
                <ul class="list-group">
                    <li class="list-group-item"><a href="/message/test">Microservice A Test Method</a></li>
                    <li class="list-group-item"><a href="/message/directEureka">Get MS B List Directly from Eureka</a></li>
                    <li class="list-group-item"><a href="/message/eurekaNextServer">Get MS B Next Server from Eureka</a></li>
                    <li class="list-group-item"><a href="/message/loadBalancer">Get MS B From Load Balancer (Ribbon)</a></li>
                    <li class="list-group-item"><a href="/message/feign">Get MS B From Feign</a></li>
                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/message" method="get">
                            <div class="form-group">
                                <label for="port">Direct Call to MS B:</label>
                                <input type="text" name="port" class="form-control" id="port" placeholder="Enter Port Number">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>
                </ul>
            </div>

        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

    </body>
</html>