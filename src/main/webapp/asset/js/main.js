function triggerActivateUser(id) {
    const params = new URLSearchParams();
    params.append('idUser', id);

    fetch("http://localhost:8080/admin/deactivate", {
        method: 'POST',
        body: params
    });
}
