document.addEventListener('click', function (event) {
    if (event.target.id !== 'custom-temp') {
        return;
    }
    let dbId = event.target.getAttribute('db-id');
    axios({
    url: 'http://3.39.165.43:5510/api/extension/manage/custom/extension',
    method: 'delete',
    data: {id: dbId}
    })
    .then(function (response) {
        console.log(response)
    })
    .catch(function (error) {
        console.log(error);
    })
    .finally(function () {
        location.reload();
    });
});