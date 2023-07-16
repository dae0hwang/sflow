document.getElementById('add-button').onclick = function () {
    let value = document.getElementById('add-input').value;
    axios({
        url: 'http://3.39.165.43:5510/api/extension/manage/custom/extension',
        method: 'post',
        data: {name: value}
    })
    .then(function (response) {
        console.log(response)
    })
    .catch(function (error) {
        console.log(error)
        alert(error.response.data.errorMessage);
    })
    .finally(function () {
        location.reload();
    });
}
