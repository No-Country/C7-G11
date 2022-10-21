# C7-G11

Integrantes del Proyecto - Cohorte 7 - G 11 M

Esteban Casile - Backend Java  
Gabriela Scire - UX/UI - Figma  
Benjamín Díaz - FrontEnd   
Guillermo Rosales Nuñez - Frontend  
Romina Bello - QA Testing Funcional Manual - Trello - Google Planillas de Cálculo  



Página web de inscripción e información sobre el Gimnasio "Lomas". 

¿Qué es Gimnasio Lomas? :
Es un gimnasio pensado para personas mayores de 18 años, que viven en Lomas de Zamora o alrededores y quieren hacer actividad física. El mismo cuenta con instalaciones de maquinas de musculación y salón de actividades para Zumba y Baile Latino. También cuenta con 4 servicios adicionales: 

Sesión nutricional: 1 sesión por semana incluída en plan Premium y 1 sesión al mes incluída en plan Vip)

Fisioterapia: 1 sesión incluida en plan Premium.

Personal Trainer: Incluído en plan Premium.

Sector guardería (todos los planes). Para niños entre 1 a 5 años. 

El gimnasio cuenta con tres planes de membresía anual: 

"Basic": (incluye 13 pases al mes de 1 hr cada uno), 
"Vip" (incluye 1 hora todos los días del mes, salvo domingos + 1 consulta nutricional mensual) y 
"Premium" (incluye 1 hora todos los días del mes, salvo domingos + 1 consulta nutricional semanal + 1 personal trainer + 1 sesión de fisioterapia mensual). 

Requerimientos:


# Membresías: 

Se podrá acceder a cualquiera de las membresías mensuales, previo registro en la web y abonando por la página con tarjeta de crédito o débito. 

El usuario podrá sacar turno a través del almanaque de la página, eligiendo día y horario (según disponibilidad). 

Para poder acceder a la elección de fechas, deberá estar con una membresía activa. 

El usuario Basic tiene una restricción de hasta 13 turnos al mes. 

Por día el usuario podrá acceder sólo a 1 turno y elegir el horario entre las 8 am y las 22 hrs.  Los turnos disponibles aparecerán en verde, mientras que los turnos ocupados aparecerán en gris. 


# Registro: 

Para contratar una membresía, el usuario se deberá registrar con mail,  nombre, apellido y fecha de nacimiento. Todos esos campos son obligatorios.

Si el usuario ingresa un mail sin @ ni .com /.ar  el usuario recibirá un mensaje: "ingrese un mail válido"

Luego de registrarse, se le pedirá al usuario que cree una contraseña que contenga 8 caracteres alfanuméricos, con al menos 1 mayúscula y al menos 1 número. 

Si la contraseña tiene menos de 8 caracteres el usuario recibirá un mensaje :"la contraseña debe contener  8 caracteres alfanuméricos, con al menos 1 mayúscula y al menos 1 número. "

Si la contraseña contiene un caracter especial, el usuario recibirá un mensaje: " la contraseña deberá contener 8 caracteres alfanuméricos, con al menos 1 mayúscula y al menos 1 número. "

Una vez que el usuario se registra, se lo redirigirá a una página de pagos de membresía, donde eligirá su plan.  

La página de pago deberá contener el nombre de la membresía elegida y el método de pago a elegir será con tarjeta de crédito o débito.

Luego que el usuario haya abonado, le llegará al mail que usó para el registro un mensaje de registro exitoso, junto con el comprobante de pago.

# Landing Page:

En la barra de navegación superior: Quiénes somos, Horarios, Servicios, Actividades, Registro, Login. 

Hay un espacio de opiniones de nuestros clientes con estrellas de calificación del plan que tomaron y de las instalaciones. 

En la parte inferior aparecerá un mapa de Google maps con la dirección del gimnasio y los datos de contacto: dirección, mail, teléfono. 

En la landing el usuario podrá observar el calendario y elegir la fecha y hora según disponibilidad. Una vez que ha realizado la elección, se lo dirigirá a la página de inicio de sesión o registro según sea el caso. 

Botones de redes sociales: Facebook, Instagram en los datos de contacto en el footer. 


En la barra inferior aparecerá "Términos y condiciones", "Política de privacidad" y "Política de cookies"

# Pagos

El usuario podrá optar por abonar con una tarjeta de débito visa siendo obligatorios los campos de Número de Tarjeta, Vencimiento, CVV. 

Al ingresar un número de tarjeta inválido o dejar algún campo incompleto aparecerá un mensaje: "ingrese un número de tarjeta válido".

Si el usuario ingresa con una tarjeta cuya fecha está caducada, el sistema emitirá el mensaje: "Ingrese una tarjeta con fecha de caducidad vigente. Si cree que es un error, contáctese con su banco". 
