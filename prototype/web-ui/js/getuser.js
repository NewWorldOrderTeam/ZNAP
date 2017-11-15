getRate();

function getUser(id){
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://znap.pythonanywhere.com/api/v1.0/user/"+id+"/", false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    user = JSON.parse(xhr.response);
    return user;
}

function getRate() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://znap.pythonanywhere.com/api/v1.0/rate/", false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    rates = JSON.parse(xhr.response);

    for (var i in rates){
        $('#list').append("<tr>" + 
            "<th class='id' scope='row'></th>" +
            "<td class='name'></td>" +
            "<td class='middle_name'></td>" +
            "<td class='second_name'></td>" +
            "<td class='email'></td>" +
            "<td class='phone'></td>" +
            "</tr>");
        user = getUser(rates[i].user_id);
        console.log(user);
        first_name = user.first_name;
        last_name = user.last_name;
        middle_name = user.middle_name;
        email = user.email;
        phone = user.phone;
        $('#list th:last .id').append(i);
        $('#list th:last .name').append(first_name);
        $('#list th:last .middle_name').append(middle_name);
        $('#list th:last .last_name').append(last_name);
        $('#list th:last .email').append(email);
        $('#list th:last .phone').append(phone);

        console.log(rates[i].dialog[0].timeStamp)
    }
} 