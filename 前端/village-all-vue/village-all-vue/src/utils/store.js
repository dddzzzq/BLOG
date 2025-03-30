
//第一步：项目封装本地存储的地方，添加cookie存储
const LocalStorage= {
    save(key, value) {    localStorage.setItem(key, JSON.stringify(value))  },
    get(key, defaultValue = {}) {
        const items = localStorage.getItem(key)
        if (items) {
            return JSON.parse(items)
        } else {
            return defaultValue
        }
    },
    remove(key) {    localStorage.removeItem(key)  },
    clear() {    localStorage.clear()  },
    /**------------------以下代码cookie存储与取值-----------*/
    setCookie(key,value){
        document.cookie=`${key}=${value}`
    },
    getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';'); //把cookie分割成组
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i]; //取得字符串
            while (c.charAt(0) == ' ') { //判断一下字符串有没有前导空格
                c = c.substring(1, c.length); //有的话，从第二位开始取
            }
            if (c.indexOf(nameEQ) == 0) { //如果含有我们要的name
                return unescape(c.substring(nameEQ.length, c.length)); //解码并截取我们要值
            }
        }
        return false;
    },
}
export default LocalStorage

