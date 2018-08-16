
import java.util.*;

public class AStack<T> {
public  T[] data;
private static final  int start_capacity =10;
private  int kappacity;
private int size = 0;
int ogstop = -1;

@SuppressWarnings("unchecked")
public AStack(){
data = (T[]) new Object[start_capacity];
kappacity =start_capacity;
}

@SuppressWarnings("unchecked")
public AStack(int capacity){
data = (T[]) new Object[capacity];
kappacity = capacity;
}





public int cap(){return kappacity;}









private  void resize(){ 
kappacity = kappacity*2;
data = Arrays.copyOf(data,kappacity);
}

public T push(T booker){
if(ogstop == data.length - 1){resize();}

ogstop++;
data[ogstop] = booker;
size++;
return booker;
}

public T pop(){
if(empty()){throw new EmptyStackException();}

size--;
return data[ogstop--];

}



public T peek(){
if(ogstop==-1){return null;}

return data[ogstop];
}

public boolean empty(){
if(size==0){return true;}
else return false;
}

public int size(){return size;}
}