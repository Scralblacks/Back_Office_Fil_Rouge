const triggerActivateUser = (id) => {
    const params = new URLSearchParams();
    params.append('idUser', id);
    fetch("http://localhost:9090/admin/deactivate", {
        method: 'POST',
        body: params
    });
}

const triggerActivateRole = (id, idRole) => {
    const params = new URLSearchParams();
    params.append('idUser', id);
    params.append('idRole', idRole);
    fetch("http://localhost:9090/admin/updateUserRoles", {
        method: 'POST',
        body: params
    });
}

const closeBtn = document.querySelector(".close.fa-solid.fa-xmark");

const closeAlert = (e) => {
    e.currentTarget.parentElement.classList.remove('display');
}

closeBtn.addEventListener('click', closeAlert);


