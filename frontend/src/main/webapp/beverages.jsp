<%@ page import="de.uniba.dsg.dsam.model.Beverage" %>
<%@ page import="java.util.List" %>
<%@ page import="de.uniba.dsg.dsam.model.Incentive" %><%--
  Created by IntelliJ IDEA.
  User: oormila
  Date: 28/11/2018
  Time: 01:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Beverages</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/additional-methods.js"></script>
    <style>
        .form-control[readonly]{
            background-color:white;
            text-align:center;
            border:none;
            box-shadow: none;
        }

        th{
            text-align:center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Beverages</h1>

    <p>
        <button class="add-new btn btn-primary">Create new Beverage</button>
    </p>

    <p>&nbsp;</p>

    <form class="formvalidation">
    <table id="tableid" class="table table-responsive" border="1">

        <tr>
            <th>Beverages ID</th>
            <th>Name</th>
            <th>Manufacturer</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Incentive</th>
            <th colspan="2">Actions</th>
        </tr>


        <tbody>
        <%
            List<Beverage> beverages = (List<Beverage>) request.getAttribute("beverageList");
            if (beverages != null) {
                for (Beverage beverage : beverages) {
        %>
            <tr id="<%=beverage.getBeverageId() %>">
                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext id"
                               name="id"
                               value="<%= beverage.getBeverageId() %>">
                    </div>
                </td>
                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext name"
                               name="name"
                               value="<%= beverage.getName()%>">
                    </div>
                </td>
                <td>
                    <div class="col-xs">
                        <input type="text" readonly
                               class="row-values form-control plaintext manufacturer" name="manufacturer"
                               value="<%= beverage.getManufacturer()%>">
                    </div>
                </td>
                <td>
                    <div class="col-xs">
                        <input type="number" min="0.5" readonly
                               class="row-values form-control plaintext price" step="0.01" name="price"
                               value="<%=String.format("%.2f",beverage.getPrice())%>">
                    </div>
                </td>
                <td>
                    <div class="col-xs">
                        <input type="number" min="1" readonly
                               class="row-values form-control plaintext quantity" name="quantity"
                               value="<%= beverage.getQuantity()%>">
                    </div>
                </td>
                <td>
                    <div class="col-xs">
                        <select readonly disabled class="form-control incentive">
                            <option value="">Select</option>
                            <%
                                List<Incentive> incentives = (List<Incentive>) request.getAttribute("incentiveList");
                                if (incentives != null) {
                                    for (Incentive incentive : incentives) {
                            %>
                            <option value="<%=incentive.getIncentiveId()%>" <%=(beverage.getIncentive() != null && incentive.getIncentiveId() == beverage.getIncentive().getIncentiveId()) ? "selected" : ""%>>
                                <%="name: " + incentive.getName() + " -  type:" + incentive.getIncentiveType()%>
                            </option>
                            <%

                                    }
                                }

                            %>
                        </select>
                    </div>
                </td>
                <td class="updatebev" style="display: none">
                    <div class="col-xs">
                        <button type="button" class="btn btn-primary updatebeverage submit">
                            Update
                        </button>
                    </div>
                </td>
                <td class="updatebev" style="display: none">
                    <div class="col-xs">
                        <button type="button" id="canceledit" class="btn btn-primary cancel">
                            Cancel
                        </button>
                    </div>
                </td>
                <td class="editbev">
                    <div class="col-xs">
                        <button type="button" class="glyphicon glyphicon-pencil btn btn-primary editbeverage">
                        </button>
                    </div>
                </td>
                <td class="deletebev">
                    <div class="col-xs">
                        <button type="button" class="glyphicon glyphicon-remove btn btn-danger deletebeverage">
                        </button>
                    </div>
                </td>
            </tr>
            <% }
            }%>
            <tr class="newitem" style="display: none">
                <td>
                </td>
                <td>
                    <input type="text" class="row-values form-control plaintext name hidden" name="name">
                </td>
                <td>
                    <input type="text" class="row-values form-control plaintext manufacturer hidden"
                           name="manufacturer">
                </td>
                <td>
                    <input type="number" min="0.5" step="0.01" class="row-values form-control plaintext price hidden"
                           name="price">
                </td>
                <td>
                    <input type="number" min="1" class="row-values form-control plaintext quantity hidden"
                           name="quantity">
                </td>
                <td>
                    <select class="form-control incentive">
                        <option value="">
                            Select
                        </option>
                        <%
                            List<Incentive> incentives = (List<Incentive>) request.getAttribute("incentiveList");
                            if (incentives != null) {
                                for (Incentive incentive : incentives) {
                        %>
                        <option value="<%=incentive.getIncentiveId()%>"><%="name: " + incentive.getName() + " -  type:" + incentive.getIncentiveType()%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </td>
                <td>
                    <button type="button" class="button btn btn-primary submit saveBeverage">Create</button>
                </td>

                <td>
                    <button type="button" class="button btn btn-primary newitemcancel cancel">Cancel</button>
                </td>
            </tr>


        </tbody>
    </table>
    </form>
    <div class="col-1" align="center"><a href="/frontend/main.jsp" class="btn btn-default">Home</a>&nbsp;&nbsp;</div>
</div>

<script type="application/javascript">
    $(".add-new").click(function () {
        $(this).attr("disabled", "disabled");
        $(".newitem").show();
        $(".newitem").children("td").children().removeClass("hidden");
        $(".newitem").children("td").children(".name,.manufacturer,.quantity,.price").attr("required", "required");
    });


    $(".cancel").click(function () {
        location.reload();
    });
    $(".submit").click(function () {

        if (!$(".formvalidation")[0].checkValidity({ignore: ':readonly,:hidden'})) {
            $(".formvalidation")[0].reportValidity();
        }
        else {
            if ($(this).hasClass("saveBeverage")) {
                saveBeverage();
            }
            else if ($(this).hasClass("updatebeverage")) {
                var tr = $(this).closest('tr');
                updatebeverageFunc(tr);
            }
        }

    });
    saveBeverage = function () {
        var beveragename = $(".newitem").children("td").children(".name").val();
        var beveragemanuf = $(".newitem").children("td").children(".manufacturer").val();
        var beverageprice = $(".newitem").children("td").children(".price").val();
        var beveragequantity = $(".newitem").children("td").children(".quantity").val();
        var incentive_id = $(".newitem").children("td").children(".incentive").val();

        $.ajax
        ({
            url: '/frontend/beveragemanagement',
            data: {
                "name": beveragename, "manufacturer": beveragemanuf,
                "price": beverageprice, "quantity": beveragequantity, "incentive_id": incentive_id
            },
            type: 'POST',
            success: function (data) {
                location.reload();
            }
        });

    }

    $(".editbeverage").click(function () {
        var tr = $(this).closest('tr');
        var elem = tr.children("td").children("div");
        tr.children('.editbev').hide();
        tr.children('.deletebev').hide();
        tr.children('.updatebev').show();
        elem.children(".name,.manufacturer,.quantity,.price,.incentive").removeAttr("readonly");
        elem.children(".name,.manufacturer,.quantity,.price").attr("required", "required");
        elem.children().removeAttr("disabled");

    });

    updatebeverageFunc = function (sRow) {
        try {
            var tr = sRow;
            var elem = tr.children("td").children("div");
            var beveragename = elem.children(".name").val();
            var beveragemanuf = elem.children(".manufacturer").val();
            var beverageprice = elem.children(".price").val();
            var beveragequantity = elem.children(".quantity").val();
            var incentive_id = elem.children(".incentive").val();
            var beverage_id = elem.children(".id").val();

            $.ajax
            ({
                url: '/frontend/beveragemanagement/edit',
                data: {
                    "id": beverage_id, "name": beveragename, "manufacturer": beveragemanuf,
                    "price": beverageprice, "quantity": beveragequantity, "incentive_id": incentive_id
                },
                type: 'post',
                success: function () {
                    location.reload();
                }
            });
        } catch (err) {
        }
    }

    $(".deletebeverage").click(function () {
     var x = confirm("Are you sure you want to delete?");
        if (x) {
        var tr = $(this).closest('tr');
        var id = tr.children("td").children("div").children(".id").val();

        $.ajax
        ({
            url: '/frontend/beveragemanagement?id=' + id,
            contentType: "application/json; charset=utf-8",
            type: 'DELETE',
            success: function (data) {
                if (data == 1)
                    location.reload();
                else
                    alert("The beverage is attached to an order")

                console.log(data);
            }
        });
         }
    });
</script>

</body>
</html>
