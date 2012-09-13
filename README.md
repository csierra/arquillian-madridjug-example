arquillian-madridjug-slides
===========================

Proyecto de ejemplo usado en la charla del Madrid JUG. 
Está hecho a partir de un archetype de jboss para demostrar cómo hacer una aplicación JEE 6.
Tiene alguna modificación con respecto al archetype original para adaptarlo a la última versión de arquillian y para adaptarlo a la charla.

En este caso, y al contrario que en el proyecto original, el pom.xml está configurado para que los tests se ejecuten siempre en el build usando un jboss administrado, por lo que debería haber una variable <code>JBOSS_HOME</code> en el ENV para que sepa dónde arrancar el JBOSS. 

Existe el profile maven arq-jbossas-remote para que los tests se ejecuten contra un contenedor ya arrancado. Útil para lanzar los tests desde el IDE contra un servidor de aplicaciones que tengamos registrado. 
