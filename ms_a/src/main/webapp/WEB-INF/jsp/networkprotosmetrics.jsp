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
                <h1>Microservice A Protos Network <small>Microservice A Protos Network </small></h1>
            </div>

            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Available Endpoints</div>
                <ul class="list-group">
                    <li class="list-group-item"><a href="/home">Home page</a></li>
                    <li class="list-group-item"><a href="/clientmetrics">Client Metrics</a></li>
                    <li class="list-group-item"><a href="/networkmetrics">Network Metrics</a></li>
                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/networkprotosmetrics/eurekaNextServer" method="get">
                            <div class="form-group">
                                <label for="calls0">Get MS B Next Server from Eureka: (/networkprotosmetrics/eurekaNextServer)</label>
                                <input type="text" name="calls" class="form-control" id="calls0" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="eurekaNextServer" placeholder="Enter amount of lines">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>
                    <li class="list-group-item">
                        <form class="form-inline" role="form" action="/networkprotosmetrics/useLoadBalancer" method="get">
                            <div class="form-group">
                                <label for="calls2">Long Message Call to MS B (Ribbon): (/networkprotosmetrics/useLoadBalancer)</label>
                                <input type="text" name="calls" class="form-control" id="calls2" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="longMessageBalancer" placeholder="Enter amount of lines">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>
                    <li class="list-group-item">

                        <form class="form-inline" role="form" action="/networkprotosmetrics/feign" method="get">
                            <div class="form-group">
                                <label for="calls3">Long Message Call to MS B (Ribbon-Feign)JSON: (/networkprotosmetrics/feign)</label>
                                <input type="text" name="calls" class="form-control" id="calls3" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="feign" placeholder="Enter amount of lines">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>

                    <li class="list-group-item">

                        <form class="form-inline" role="form" action="/networkprotosmetrics/feignproto" method="get">
                            <div class="form-group">
                                <label for="calls3">Long Message Call to MS B (feign) Proto Buffers: (/networkprotosmetrics/feignproto)</label>
                                <input type="text" name="calls" class="form-control" id="calls4" placeholder="Enter amount of calls to B">
                                <input type="text" name="lines" class="form-control" id="feign2" placeholder="Enter amount of lines">
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