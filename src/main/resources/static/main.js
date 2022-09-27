function getAllUsers() {
    fetch("http://localhost:8080/api/users")
        .then(res => res.json())
        .then(users => {
            let temp = '';
            console.log(users);
            users.forEach(function (user) {
                temp += `
                <tr>
                <td id="id${user.id}">${user.id}</td>
                <td id="username${user.id}">${user.name}</td> 
                <td id="surname${user.id}">${user.surname}</td> 
                <td id="age${user.id}">${user.age}</td>
                <td id="email${user.id}">${user.email}</td>
                <td id="roles${user.id}">${user.roles.map(r => r.name)}</td>
                <td>
                <button class="btn btn-info btn-md" type="button"
                data-toggle="modal" data-target="#modalEdit" 
                onclick="openModal(${user.id})">Edit</button></td>
                <td>
                <button class="btn btn-danger btn-md" type="button"
                data-toggle="modal" data-target="#modalDelete" 
                onclick="openModal(${user.id})">Delete</button></td>
              </tr>`;
            });
            document.getElementById("data").innerHTML = temp;
        });
}
getAllUsers()

function openModal(id) {
    fetch("http://localhost:8080/api/users/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {
            console.log(u);
            document.getElementById('id').value = u.id;
            document.getElementById('editUsername').value = u.name;
            document.getElementById('editSurname').value = u.surname;
            document.getElementById('editAge').value = u.age;
            document.getElementById('editEmail').value = u.email;
            document.getElementById('editPassword').value = u.age;
            document.getElementById('editRole').value = u.age;

            document.getElementById('delId').value = u.id;
            document.getElementById('delUsername').value = u.name;
            document.getElementById('delSurname').value = u.surname;
            document.getElementById('delAge').value = u.age;
            document.getElementById('delEmail').value = u.email;
            document.getElementById('delPassword').value = u.age;
            document.getElementById('delRoles').value = u.age;
        })
    });
}

function refreshTable() {
    let table = document.getElementById('data')
    while (table.rows.length > 1) {
        table.deleteRow(1)
    }
    getAllUsers()
}

async function deleteUser() {
    await fetch("http://localhost:8080/api/delete/" + document.getElementById("delId").value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
    })

    $("#modalDelete .close").click();
    refreshTable();
}

function showUserInfo() {
    fetch('http://localhost:8080/api/userMain')
        .then((res) => res.json())
        .then((user) => {
            let temp = "";
            temp += `<tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles[0].name}</td>
            </tr>`;
            document.getElementById("userMain").innerHTML = temp;
        });
}

showUserInfo();

document.getElementById("NewUserForm")
    .addEventListener("submit", addNewUser);

function addNewUser() {
    let username = document.getElementById('newUsername').value;
    let surname = document.getElementById('newSurname').value;
    let age = document.getElementById('newAge').value;
    let email = document.getElementById('newEmail').value;
    let password = document.getElementById('newPassword').value;
    let roles = getRoles(Array.from(document.getElementById('newRole').selectedOptions)
        .map(r => r.value));
    fetch("http://localhost:8080/api/newUser", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            name: username,
            surname: surname,
            age: age,
            email: email,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            document.getElementById("allUsersClick").click();
            getAllUsers();
            document.getElementById("NewUserForm").reset();
        })
}

function getRoles(list) {
    let roles = [];
    if (list.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    if (list.indexOf("USER") >= 0) {
        roles.push({"id": 2});
    }
    return roles;
}

document.getElementById("editForm")
    .addEventListener("submit", editUser);

async function editUser() {
    await fetch('http://localhost:8080/api/update', {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            id: document.getElementById('id').value,
            name: document.getElementById('editUsername').value,
            surname: document.getElementById('editSurname').value,
            email: document.getElementById('editEmail').value,
            age: document.getElementById('editAge').value,
            password: document.getElementById('editPassword').value,
            roles: getRoles(Array.from(document.getElementById('editRole').selectedOptions)
                .map(r => r.value))
        })
    }).then(response => console.log(response));
}