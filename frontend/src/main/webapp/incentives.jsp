<%@ page import="de.uniba.dsg.dsam.model.Incentive" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: oormila
  Date: 28/11/2018
  Time: 01:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incentives</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
    <style>
        .form-control[readonly] {
            background-color: white;
            text-align: center;
            border: none;
            box-shadow: none;
        }

        th {
            text-align: center;
        }
        table{
            width: max-content;
        }

    </style>
</head>
<body>
<div class="container">
    <h1>Incentives</h1>

    <p>
        <button class="add-new btn btn-primary">Create new incentive</button>
    </p>

    <p>&nbsp;</p>

    <form class="formvalidation">
        <table id="tableid" class="table table-responsive" border="1">

            <tr>
                <th>Incentive ID</th>
                <th>Incentive Type</th>
                <th>Name</th>
                <th colspan="2">Actions</th>
            </tr>


            <tbody>
            <%
                List<Incentive> incentives = (List<Incentive>) request.getAttribute("incentiveList");
                if (incentives != null) {
                    for (Incentive incentive : incentives) {
            %>
            <tr id="<%=incentive.getIncentiveId() %>">
                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext id" name="id"
                               value="<%= incentive.getIncentiveId() %>">
                    </div>
                </td>
                <td>
                    <div class="col-xs">
                        <select disabled readonly class="form-control type" name="type">
                            <%
                                List<String> incentivesTypes = (List<String>) request.getAttribute("incentiveTypes");
                                if (incentivesTypes != null) {
                                    for (String incentivesType : incentivesTypes) {
                            %>
                            <option value="<%=incentivesType%>" <%=(incentivesType.equals(incentive.getIncentiveType())) ? "selected" : ""%>>
                                <%=incentivesType%>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </td>
                <td>
                    <div class="col-xs">
                        <input type="text" readonly class="row-values form-control plaintext name" name="name"
                               value="<%= incentive.getName()%>">
                    </div>
                </td>
                <td class="updateinc" style="display: none">
                    <div class="col-xs">
                        <button type="button" class=" btn btn-primary updateincentive submit">
                            Update
                        </button>
                    </div>
                </td>

                <td class="updateinc" style="display: none">
                    <div class="col-xs">
                        <button class=" btn btn-primary cancel">
                            Cancel
                        </button>
                    </div>
                </td>

                <td class="editinc">
                    <div class="col-xs">
                        <button type="button" class="glyphicon glyphicon-pencil btn btn-primary editincentive">
                        </button>
                    </div>
                </td>
                <td class="deleteinc">
                    <div class="col-xs">
                        <button type="button" class="glyphicon glyphicon-remove btn btn-danger deleteincentive">
                        </button>
                    </div>
                </td>
            </tr>
            <% }
            }%>
            <tr class="newitem" style="display: none">
                <td></td>
                <td>
                    <select class="typeinput form-control hidden" name="typei">
                        <%
                            List<String> incentivesTypes = (List<String>) request.getAttribute("incentiveTypes");
                            if (incentivesTypes != null) {
                                for (String incentivesType : incentivesTypes) {
                        %>
                        <option><%=incentivesType%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </td>
                <td><input type="text" class="nameinput hidden form-control" name="namei"></td>
                <td>
                    <button type="button" class="button btn btn-primary saveIncentive submit">Create</button>
                </td>
                <td>
                    <button type="button" class="button btn btn-primary cancel">Cancel</button>
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
        $(".newitem").children("td").children(".nameinput,.typeinput").attr("required", "required");
    });
    $(".cancel").click(function () {
        location.reload();
    });


    $(".submit").click(function () {

        if (!$(".formvalidation")[0].checkValidity({ignore: ':readonly,:hidden'})) {
            $(".formvalidation")[0].reportValidity();
        }
        else {
            if ($(this).hasClass("saveIncentive")) {
                saveincentive();
            }
            else if ($(this).hasClass("updateincentive")) {
                var tr = $(this).closest('tr');
                updateincentive(tr);
            }
        }

    });

    saveincentive = function () {
        var incentivetype = $(".newitem").children("td").children(".typeinput").val();
        var incentivename = $(".newitem").children("td").children(".nameinput").val();
        $.ajax
        ({
            url: '/frontend/incentives',
            data: {"name": incentivename, "type": incentivetype},
            type: 'POST',
            success: function () {
                location.reload();
            }
        });
    }

    $(".editincentive").click(function () {
        var tr = $(this).closest('tr');
        var elem = tr.children("td").children("div");
        tr.children('.editinc').hide();
        tr.children('.deleteinc').hide();
        tr.children('.updateinc').show();
        elem.children(".name,.type").removeAttr("readonly");
        elem.children(".name,.type").attr("required", "required");
        elem.children().removeAttr("disabled");
    });

    updateincentive = function (sRow) {
        var tr = sRow;
        var name = tr.children("td").children("div").children(".name").val();
        var type = tr.children("td").children("div").children(".type").val();
        var id = tr.children("td").children("div").children(".id").val();

        $.ajax
        ({
            url: '/frontend/incentives/edit',
            data: {"id": id, "name": name, "type": type},
            type: 'post',
            success: function () {
                location.reload();
            }
        });
    }

    $(".deleteincentive").click(function () {
        var x = confirm("Are you sure you want to delete?");
        if (x) {
            var tr = $(this).closest('tr');
            var id = tr.children("td").children("div").children(".id").val();

            $.ajax
            ({
                url: '/frontend/incentives?id=' + id,
                contentType: "application/json; charset=utf-8",
                type: 'DELETE',
                success: function (data) {
                    if (data == 1)
                        location.reload();
                    else
                        alert("The incentive is attached to an order")

                    console.log(data);
                }
            });
        }
    });
</script>
</body>
</html>
