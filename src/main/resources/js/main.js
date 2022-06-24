function openPopup(id) {
    //"/admin/user/" + id
    fetch('/api/user/' + id, { method: 'GET' })
        .then(function(response) { return response.json(); })
        .then(function(json) {
        fillingForm(json);
        $('#exampleModalScrollable').modal('toggle');
        $('.action_wrap').html('<button onclick="updateUser();" class="btn btn-primary modal-submit">Edit</button>');
        $('.modal-title').text('Edit user');
    });
}

function openPopupDelete(id) {
    fetch('/api/user/' + id, { method: 'GET' })
        .then(function(response) { return response.json(); })
        .then(function(json) {
            fillingForm(json, true);
            $('#exampleModalScrollable').modal('toggle');
            $('.action_wrap').html('<button onclick="deleteUser(' + id + ');" class="btn btn-danger modal-submit">Delete</button>');
            $('.modal-title').text('Delete user');
        });
}

function fillingUsersTable() {
    fetch('/api/users', { method: 'GET' })
        .then(function(response) { return response.json(); })
        .then(function(json) {
            let uTable = $('#users_table');
            uTable.html('');
            json.map(user => {
                uTable.append('<tr>' +
                    '<td><span>' + user.id + '</span></td>\n' +
                    '<td><span>' + user.firstName + '</span></td>\n' +
                    '<td><span>' + user.lastName + '</span></td>\n' +
                    '<td><span>' + user.age + '</span></td>\n' +
                    '<td><span>' + user.email + '</span></td>\n' +
                    '<td><span>' + user.rolesString + '</span></td>\n' +
                    '<td>\n' +
                    '<button onclick="openPopup(' + user.id + ')" type="button" class="btn btn-info btn_edit">\n' +
                    'Edit\n' +
                    '</button>\n' +
                    '</td>\n' +
                    '<td>\n' +
                    '<button onclick="openPopupDelete(' + user.id + ')" type="button" class="btn btn-danger btn_delete">\n' +
                    'Delete\n' +
                    '</button>\n' +
                    '</td>\n' +
                    '</tr>');
            })
        });

}


function updateUser() {
    const data = new URLSearchParams();
    for (const pair of new FormData(document.getElementById('user-form-update'))) {
        data.append(pair[0], pair[1]);
    }

    fetch("/api/user/update", {
        method: 'put',
        body: data,
    }).then(resp => {
        $('#exampleModalScrollable').modal('toggle');
        fillingUsersTable();
    });
}

function createUser() {
    const data = new URLSearchParams();
    for (const pair of new FormData(document.getElementById('user-form-create'))) {
        data.append(pair[0], pair[1]);
    }
    fetch("/api/user", {
        method: 'post',
        body: data,
    }).then(resp => {
        fillingUsersTable();
        $('[href="#list"]').tab('show');
        $(':input', '#user-form-create')
            .not(':button, :submit, :reset, :hidden')
            .val('')
            .prop('checked', false)
            .prop('selected', false);
    });
}

function deleteUser(id) {
    fetch(`/api/user/${id}/delete`, {
        method: 'delete',
    }).then(resp => {
        $('#exampleModalScrollable').modal('toggle');
        fillingUsersTable();
    });
}

function fillingForm(json, readonly = false) {
    $('.modal-body').html('<div class="modal-body">\n' +
        '<div>\n' +
        '    <form id="user-form-update" class="center-block">\n' +
        '        <div class="mx-auto text-center" style="width: 400px;">\n' +
        '\n' +
        '            <div>\n' +
        '                <label class="label_input" for="inputID">ID</label>\n' +
        '                <input type="text" placeholder="ID" class="form-control" id="inputID" readonly="" name="id" value="' + json.id + '">\n' +
        '            </div>\n' +
        '\n' +
        '        <label class="label_input" for="inputFirstName">First name</label>\n' +
        '        <input type="text"  ' + (readonly && 'readonly') + ' placeholder="First name" class="form-control" id="inputFirstName" name="firstName" value="' + json.firstName + '">\n' +
        '\n' +
        '        <label class="label_input" for="inputLastName">Last name</label>\n' +
        '        <input type="text" ' + (readonly && 'readonly') + ' placeholder="Last name" class="form-control" id="inputLastName" name="lastName" value="' + json.lastName + '">\n' +
        '\n' +
        '        <label class="label_input" for="inputAge">Age</label>\n' +
        '        <input type="number" ' + (readonly && 'readonly') + ' placeholder="Age" class="form-control" id="InputAge" name="age" value="' +  json.age + '">\n' +
        '\n' +
        '        <label class="label_input" for="inputEmail">Email</label>\n' +
        '        <input type="email" ' + (readonly && 'readonly') + ' placeholder="Email" class="form-control" id="inputEmail" name="email" value="' + json.email + '">\n' +
        '\n' +
        '        <label class="label_input" for="inputPassword">Password</label>\n' +
        '        <input type="password" ' + (readonly && 'readonly') + ' placeholder="Password" class="form-control" id="inputPassword" name="password" value="">\n' +
        '\n' +
        '        <label class="label_input">Role</label>\n' +
        '        <select style="opacity: 1;" ' + (readonly && 'disabled') + ' name="roless" class="custom-select" multiple="" size="2">\n' +
        '            <option value="2">admin</option>\n' +
        '            <option value="1">user</option>\n' +
        '        </select>\n' +
        '\n' +
        '        \n' +
        '        </div>\n' +
        '    </form>\n' +
        '</div>\n' +
        '\n' +
        '\n' +
        '</div>');
}