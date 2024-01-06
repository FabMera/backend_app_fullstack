###
Link App frontend en produccion : http://login-modyo-vue.s3-website-us-west-1.amazonaws.com/#/
- API REST para proyecto full stack ,se encuentra en produccion en amazon web services S2 (INSTANCIA)
- Construcción backend con springboot 3.X.X y Spring  Security 6 ,Utiliza Jason Web Token para la sesion del usuario oauth2.
- La api esta diseñada para el login y registro de usuarios con un frontend en vuejs 3.
- La conexion a la base de datos se realiza a traves de PostGresql.
- La única restricción es el email, que es unico, por ende la validación se realiza en base a este argumento.
- Se crean excepciones personalizadas para mostrarlas en el frontend.
