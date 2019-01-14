<%@ page import="java.util.List" %>
<%@ page import="de.uniba.dsg.dsam.model.SelectedItem" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: sini ann
  Date: 09/12/18
  Time: 12:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thank you</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">

</head>
<body>
<div class="container" align="center">
    <form action="confirmOrder.jsp" method="post">
        <h3>Thank you for your order!</h3>
        <table border="1" class="table table-striped table-responsive">

            <tr>
                <th>Beverage Manufacturer</th>
                <th>Beverage Name</th>
                <th>Beverage Price</th>
                <th>Incentive</th>
                <th>Order Quantity</th>
            </tr>


            <tbody>

            <%
                List<SelectedItem> beverages = (List<SelectedItem>)request.getAttribute("selectedItems");
                for (SelectedItem item  : beverages) {

            %>
            <tr>
                <td><div class="col-xs-1"><%= item.getManufacturer() %></div></td>
                <td><div class="col-xs-1"><%= item.getName()%></div></td>
                <td><div class="col-xs-1"><%= String.format("%.2f",item.getPrice())%></div></td>
                <td><div class="col-xs-1"><%= item.getIncentive()!=null?"Name: "+item.getIncentive().getName() + " -  "+"Type: "+item.getIncentive().getIncentiveType():" No incentive"%></div></td>
                <td><div class="col-xs-1"><%= item.getOrderQuantity()%></div></td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <div class="col-1" align="center"><a href="/frontend/main.jsp" class="btn btn-default">Home</a>&nbsp;&nbsp;

        </div>

    </form>

</div>

</body>
</html>
