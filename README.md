# Laboratorio 2
## Part I

1. La clase responsable de que el programa sea ineficiente o de que el consumo de CPU sea elevado es Consumer, ya que todo el tiempo está preguntando si hay productos para consumir y el productor solo produce cada cierto tiempo.

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/1.PNG "Ineficiente. Consumo inicial.")

2. Al corregirlo, haciendo que el productor notifique cada vez que produzca, el consumo de CPU se reduce de manera notoria.
 
![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/2.PNG "Modificado")

3. Al hacer que el productor produzca más rápido y el consumidor consuma lentamente, teniendo en cuenta el stock limit, no se aumenta el consumo de CPU.

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/3.PNG "Arreglado")

## Part II

2. Para este caso, para N jugadores, ¿cuál debería ser el valor de las vidas? 
- Este valor debería ser N*100 que es el valor inicial que se le da a cada jugador.

3. Corre la aplicación y verifica cómo trabaja la opción 'pause and check'. ¿Se cumple el invariante?
- No se cumple el invariante, la suma total de las vidas siempre da un valor distinto. Además, al oprimir 'pause and check' da una suma, sin pausar el juego.

4. Para hacer funcionar el 'pause and check' y que se hiciera una suma correcta, se creó una variable atómica para poder manejar el tema de la sincronización de los hilos, como se muestra a continuación, donde la variable atómica se llama 'pausarHilo'.

- En ControlFrame en 'pause and check':

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/4.PNG)  

- En ControlFrame en 'resume':

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/5.PNG)

- En Immortal:

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/6.PNG)

5. Revisa la operación de nuevo. ¿Se cumple el invariante o no?
- Sí se cumple el invariante, como se muestra en las imágenes.

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/7.PNG)
![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/8.PNG)

6. Posibles regiones críticas. 
- En el momento en que el inmortal pelea, en el método 'fight', como se cambian la vida del atacante y del que ataca, se puede dar el caso donde más de un hilo esté modificando estos valores al mismo tiempo.

11- Para implementar STOP se creó el booleano 'running', el cual inicia en true indicando que el programa está activo.

- En Immortal:

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/9.PNG)
![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/10.PNG)

- En ControlFrame:

![](https://raw.githubusercontent.com/Nattpalacios/ARSW-Lab3/master/Imagenes/11.PNG)
