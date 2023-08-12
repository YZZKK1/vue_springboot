module.exports={
    devServe:{
        port:9876,
        proxy:{
            "api":{
                target:"http://localhost:9090",
                changeOrigin:true,
                pathRewrite: {
                    "/api":""
                }
            }
        }
    }
}