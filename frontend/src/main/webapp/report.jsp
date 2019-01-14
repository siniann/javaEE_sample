<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Report Management</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
    <style>
        .table-wrapper-scroll-y {
            display: block;
            max-height: 200px;
            overflow-y: auto;
            -ms-overflow-style: -ms-autohiding-scrollbar;
        }
        .dropdown-submenu {
            position: relative;
        }

        .dropdown-submenu .dropdown-menu {
            top: 0;
            left: 100%;
            margin-top: -1px;
        }
        #reporttableheadingid h3,
        #reporttableheadingid p {
            display: inline;
            vertical-align: top;
            line-height: 28px;
        }
    </style>

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
        </div>
        <ul class="nav navbar-nav">
            <li><a class="navbar-link" href="/frontend">Home</a></li>
            <li><a class="navbar-link" href="/frontend/createorder">Orders</a></li>
            <li><a class="navbar-link" href="/frontend/beveragemanagement">Beverages</a></li>
            <li><a class="navbar-link" href="/frontend/incentives">Incentives</a></li>
        </ul>
    </div>
</nav>
<h1 class="display-4" align="center">Report Management</h1>
<div align="center">
    <h4>Select dates </h4>
    <input type="text" name="daterange" value="10/23/2018 - 02/15/2019" />
</div>
<div class="container">
    <div id="reporttableid" class ="panel panel-default">
        <div class="panel-heading" id="reporttableheadingid">
            <h3>Order Summary</h3>  <p id="parareporttableheadingid"></p>

            <div class="dropdown">
                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">Sort By
                    <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li class="dropdown-submenu">
                        <a class="test" tabindex="-1" href="#">Order ID <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><button tabindex="-1" value="order asc" onclick="sortby(this)">ascending</button></li>
                            <li><button tabindex="-1" value="order dsc" onclick="sortby(this)">decending</button></li>
                        </ul>
                    </li>
                    <li class="dropdown-submenu">
                        <a class="test" tabindex="-1" href="#">Date <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><button tabindex="-1" value="date asc" onclick="sortby(this)">ascending</button></li>
                            <li><button tabindex="-1" value="date dsc" onclick="sortby(this)">decending</button></li>
                        </ul>
                    </li>
                    <li class="dropdown-submenu">
                        <a class="test" tabindex="-1" href="#">Price <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><button tabindex="-1" value="price asc" onclick="sortby(this)">ascending</button></li>
                            <li><button tabindex="-1" value="price dsc" onclick="sortby(this)">decending</button></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="table-responsive container table-wrapper-scroll-y" >
                <table class="table ">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Order ID</th>
                        <th>Purchased Items</th>
                        <th>Purchased Date</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody id="table_body_id">
                    </tbody>
                </table>
        </div>
        <div class="table-responsive container">
            <h5 id="Revenueid"></h5>
        </div>
    </div>
    <div class = "panel panel-default" id="ordersbyincenticesid">
        <div class="panel-heading">
            <h3>Orders By Incentives</h3>
        </div>
        <div class="table-responsive container" >
            <table class="table ">
                <thead>
                <tr>
                    <th>Incentive Type</th>
                    <th>Number of Items Brought</th>
                    <th>Items Brought</th>
                    <th>Revenue Generated</th>
                    <th>Revenue Share</th>
                </tr>
                </thead>
                <tbody id="incentive_table_body_id">
                </tbody>
            </table>
        </div>
    </div>
</div>


<div class="modal fade" id="myModal" role="dialog" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
        <img src="https://loading.io/spinners/coolors/lg.palette-rotating-ring-loader.gif">
    </div>
</div>

</body>
<script type="text/javascript">
    function getData(start_date,end_date)
    {
        $("#myModal").modal();
        $("#table_body_id").empty();
        $("#Revenueid").empty();
        $("#incentive_table_body_id").empty();
        $("#parareporttableheadingid").empty();

        $.ajax
        ({
            type: "get",
            url: "/frontend/reportdata",
            data: {
                startDate: start_date,
                endDate: end_date,
            },
            success: function(obj)
            {
                var data = JSON.parse(obj);
                tableData(data);

                var data = JSON.parse(obj);
                incentivesData(data);
                $("#myModal").modal('hide');
            }
        });
    }
    getData("2018-10-15","2019-03-20");

    var tabledata = [];

    function tableData(data)
    {
        for(var i=0;i<data.length;) {
            var obj = data.shift();
            dict = {};
            dict["cus_orderid"] = obj.cus_orderid;
            dict["incentive_name"] = obj.incentive_name;
            dict["incentive_type"] = obj.incentive_type;
            dict["manufacturer"] = obj.manufacturer;
            dict["orderdate"] = obj.orderdate;
            var list = obj.name + "(" + obj.quantity + "no.)";
            var price = obj.price * obj.quantity;
            for (j = 0; j < data.length; j++) {
                if (obj.cus_orderid == data[j].cus_orderid) {
                    list = list + ", " + data[j].name + "(" + data[j].quantity + "no.)";
                    price = price + data[j].quantity * data[j].price;
                    data.splice(j,1);
                }
            }
            dict["items"] = list;
            dict["price"] = price;
            tabledata.push(dict);

        }
            // console.log("details = "+details);
            displayTable(tabledata);
    }

    function displayTable(details) {
        $("#table_body_id").empty();
        $("#Revenueid").empty();
        $("#parareporttableheadingid").empty();

        var sum = 0;
        var counter = 0;
        for(i=0;i<details.length;i++) {
            counter++;
            var display = "<tr><td>"+ counter + "</td><td>" + details[i].cus_orderid + "</td><td>" + details[i].items + "</td><td>" + details[i].orderdate + "</td><td>&euro;" + details[i].price + "</td></tr>"
            $("#table_body_id").append(display);
            sum += details[i].price;
        }
        var display = "<tr><td></td><td></td><td></td><td></td><td><strong>Total Revenue = &euro;"+sum+"</strong></td></tr>"
        $("#table_body_id").append(display);
        $("#parareporttableheadingid").append("(showing "+counter+" items)")
        $("#Revenueid").append("<strong>Total Revenue = &euro;"+sum+"</strong>");
    }

    function incentivesData(data)
    {
        var details = [];
        var dict = {};
        var dict2 = {};
        var dict3 = {};
        dict["incentive_type"] = "Trial Package";
        dict2["incentive_type"] = "Promotional Gift";
        dict3["incentive_type"] = "No Incentive"
        var arr = [];var arr2=[];var arr3=[];
        var price = 0;var price2 =0;var price3=0;
        var q = [0,0,0]
        for(var i=0;i<data.length;i++) {

            if(data[i].incentive_type == "trialpackage")
            {
                price += data[i].price * data[i].quantity;
                arr.push(data[i].name);
                q[0] = q[0] + data[i].quantity
            }
            else if(data[i].incentive_type == "promotionalgift")
            {
                price2 += data[i].price * data[i].quantity;
                arr2.push(data[i].name);
                q[1] = q[1] + data[i].quantity
            }
            else
            {
                price3 += data[i].price * data[i].quantity;
                arr3.push(data[i].name);
                q[2] = q[2] + data[i].quantity
            }
        }
        dict["list"] = [...new Set(arr)];
        dict2["list"] = [...new Set(arr2)];
        dict3["list"] = [...new Set(arr3)];

        dict["price"] = price
        dict2["price"] = price2
        dict3["price"] = price3;

        dict["items"] = q[0]
        dict2["items"] = q[1]
        dict3["items"] = q[2];

        details.push(dict);
        details.push(dict2);
        details.push(dict3);
        displayIncentiveTable(details)
    }
    function displayIncentiveTable(details)
    {
        var total = 0;
        var itemsum = 0;
        for(i=0;i<details.length;i++)
        {
            total += details[i].price;
            itemsum += details[i].items;
        }
        for(i=0;i<details.length;i++)
        {
            var display = "<tr><td>"+details[i].incentive_type+"</td><td>"+details[i].items+"</td><td>"+details[i].list+"</td><td>&euro;"+details[i].price+"</td><td>"+parseFloat(details[i].price*100/total).toFixed(1)+"%</td></tr>"
            $("#incentive_table_body_id").append(display);
        }
        display = "<tr><td></td><td><strong>Total Items = "+itemsum+"</strong></td><td></td><td><strong>Total Revenue = &euro;"+total+"</strong></td><td></td></tr>";
        $("#incentive_table_body_id").append(display);

    }

    $(function() {
        $('input[name="daterange"]').daterangepicker({
            opens: 'left'
        }, function(start, end, label) {
            console.log("A new date selection was made: " + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
            getData(start.format('YYYY-MM-DD'),end.format('YYYY-MM-DD'));
        });
    });
    $(document).ready(function(){
        $('.dropdown-submenu a.test').on("click", function(e){
            $(this).next('ul').toggle();
            e.stopPropagation();
            e.preventDefault();
        });
    });
    function sortby(value) {
        if(value.value == "order asc")
        {
            displayTable(tabledata.sort((a,b) => (a.cus_orderid > b.cus_orderid) ? 1 : ((b.cus_orderid > a.cus_orderid) ? -1 : 0)));
        }
        else if(value.value == "order dsc")
        {
            displayTable(tabledata.sort((a,b) => (a.cus_orderid < b.cus_orderid) ? 1 : ((b.cus_orderid < a.cus_orderid) ? -1 : 0)));
        }
        else if(value.value == "date asc")
        {
            displayTable(tabledata.sort((a,b) => (a.orderdate > b.orderdate) ? 1 : ((b.orderdate > a.orderdate) ? -1 : 0)));
        }
        else if(value.value == "date dsc")
        {
            displayTable(tabledata.sort((a,b) => (a.orderdate < b.orderdate) ? 1 : ((b.orderdate < a.orderdate) ? -1 : 0)));
        }
        else if(value.value == "price asc")
        {
            displayTable(tabledata.sort((a,b) => (a.price > b.price) ? 1 : ((b.price > a.price) ? -1 : 0)));
        }
        else if(value.value == "price dsc")
        {
            displayTable(tabledata.sort((a,b) => (a.price < b.price) ? 1 : ((b.price < a.price) ? -1 : 0)));
        }
    }
</script>
</html>
