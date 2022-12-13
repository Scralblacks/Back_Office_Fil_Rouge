const triggerActivateUser = (id) => {
    const params = new URLSearchParams();
    params.append('idUser', id);

    fetch("http://localhost:8080/admin/deactivate", {
        method: 'POST',
        body: params
    });
}

const triggerActivateRole = (id, idRole) => {
    const params = new URLSearchParams();
    params.append('idUser', id);
    params.append('idRole', idRole);
    fetch("http://localhost:8080/admin/updateUserRoles", {
        method: 'POST',
        body: params
    });

}