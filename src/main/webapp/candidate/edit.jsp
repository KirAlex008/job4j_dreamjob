<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


        <script>
            $(document).ready(function () {
                $.ajax({
                    url: 'http://localhost:8080/dreamjob/cities',
                    type: "GET",
                    dataType: "json",
                    success: function(data){
                        var data2 = JSON.parse(JSON.stringify(data))
                        var options = $("#city");
                        $.each(data, function(index) {
                            options.append($("<option />").val(data2[index].id).text(data2[index].name));
                        });
                    }
                });
            })

            function validate() {
                let formFieldName = $('#name').val();
                let formFieldCity = $('#city').val()
                let missingFields = '';

                if (formFieldName === '') {
                    missingFields += " name ";
                }

                if (formFieldCity === '') {
                    missingFields += " city ";
                }

                if (missingFields !== '') {
                    alert("Enter next fields: " + missingFields);
                    return false;
                }

                if (missingFields === '') {
                return true;
            }
            }

    </script>
    <title>Работа мечты</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate cand = new Candidate(0, "");
    if (id != null) {
        cand = PsqlStore.instOf().findByIdC(Integer.valueOf(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>
    </div>

    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>

            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=cand.getId()%>" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <label>
                            <input type="text" class="form-control" value="<%=cand.getName()%>" id="name" name="name">
                        </label>
                    </div>
                    <div class="form-group">
                        <select name="city" id="city" >
                            <option></option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate();" >Сохранить</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
