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
                    <li class="list-group-item"><a href="/ms-a/test">Microservice A Test Method</a> (/ms-a/test)</li>
                    <li class="list-group-item"><a href="/ms-a/directEureka">Get MS B List Directly from Eureka</a> (/ms-a/directEureka)</li>
                    <li class="list-group-item"><a href="/ms-a/loadBalancer">Get MS B From Load Balancer (Ribbon)</a> (/ms-a/loadBalancer)</li>
                    <li class="list-group-item"><a href="/ms-a/feign">Get MS B From Feign</a> (/ms-a/feign)</li>
                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/ms-a/direct" method="get">
                            <div class="form-group">
                                <label for="server">Direct Call to MS B: (/ms-a/direct)</label>
                                <input type="text" name="server" class="form-control" id="server" placeholder="Enter server name">
                                <input type="text" name="port" class="form-control" id="port" placeholder="Enter Port Number">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>
                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/ms-a/eurekaNextServer" method="get">
                            <div class="form-group">
                                <label for="calls0">Get MS B Next Server from Eureka: (/ms-a/eurekaNextServer)</label>
                                <input type="text" name="calls" class="form-control" id="calls0" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="eurekaNextServer" placeholder="Enter amount of lines">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>
                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/ms-a/longMessageFeign" method="get">
                            <div class="form-group">
                                <label for="calls1">Long Message Call to MS B (Feign): (/ms-a/longMessageFeign)</label>
                                <input type="text" name="calls" class="form-control" id="calls1" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="longMessage" placeholder="Enter amount of lines">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>
                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/ms-a/longMessageBalancer" method="get">
                            <div class="form-group">
                                <label for="calls2">Long Message Call to MS B (Ribbon): (/ms-a/longMessageBalancer)</label>
                                <input type="text" name="calls" class="form-control" id="calls2" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="longMessageBalancer" placeholder="Enter amount of lines">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>

                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/ms-a/longMessageTransferFeign" method="get">
                            <div class="form-group">
                                <label for="calls2">Long Message(tranfer time) Call to MS B (Ribbon): (/ms-a/longMessageTransferFeign)</label>
                                <input type="text" name="calls" class="form-control" id="calls3" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="longMessageBalancer2" placeholder="Enter amount of lines">
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