document.getElementById('registerForm').addEventListener('submit', function (event) {
    event.preventDefault(); // 阻止表单默认提交行为

    // 获取用户名和密码的输入值
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // 构造请求体数据
    const data = {
        username: username,
        password: password
    };

    // 发送POST请求到后端 /user/register 接口
    fetch('http://localhost:7777/user/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // 指定发送数据的格式为JSON
        },
        body: JSON.stringify(data) // 将JavaScript对象转换为JSON字符串
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('请求失败');
            }
            return response.json(); // 解析响应数据为JSON格式
        })
        .then(data => {
            // 处理后端返回的数据
            console.log('成功:', data);
            alert('注册成功！');
        })
        .catch(error => {
            // 处理错误情况
            console.error('错误:', error);
            alert('注册失败，请重试！');
        });
});
