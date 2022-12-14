const userPanelData = document.getElementById("user_panel-data");
const authorisedUserData = document.getElementById("authorised_user-data");

let currentUser = () => {
    fetch("http://localhost:8080/api/user", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(user => {
            if (user != null) {
                userPanelData.innerHTML = `
                                <tr>
                                    <td> ${user.id} </td>
                                    <td> ${user.name} </td>
                                    <td> ${user.lastName} </td>
                                    <td> ${user.username} </td>
                                    <td> ${user.roles.map((role) => role.name === "ROLE_USER" ? "User" : "Admin")} </td>
                                </tr>
                        `
                authorisedUserData.innerHTML = `
                    <p class="d-inline font-weight-bold">${user.username} With role ${user.roles.map((role) => role.name === "ROLE_USER" ? "User" : "Admin")}</p>`
            }
        })
}
currentUser();