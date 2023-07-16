document.addEventListener('click', function (event) {
    if (event.target.parentElement.id !== 'default-temp') {
        return;
    }
    let dbId = event.target.getAttribute('db-id');
    axios({
    url: 'http://3.39.165.43:5510/api/extension/manage/default/active',
    method: 'post',
    data: {id: dbId}
    })
    .then(function (response) {
        console.log(response)
    })
    .catch(function (error) {
        console.log(error)
    })
    .finally(function () {
    });
});