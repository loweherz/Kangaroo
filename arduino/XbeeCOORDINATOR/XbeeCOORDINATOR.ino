#include <SoftwareSerial.h>

SoftwareSerial xbee(10, 9);

String tmp;
int c=0;

void setup(){  
  Serial.begin(19200);  
  Serial.println("COORDINATOR");     
  xbee.begin(19200); 
  delay(10);  
}

void loop() {
  
  if((xbee.available()>0)) {    
    char lettura = (char) xbee.read();
    Serial.print(lettura); 
    //delay(20);
  }  
  
  if((Serial.available()>0)){
    char lettura = (char) (Serial.read());
    xbee.print(lettura);
    xbee.flush();
    //Serial.print(lettura);
    Serial.flush();
    delay(10);
    //c++;
    //Serial.println(c);
    //if(c==3){
      //c=0;
    //  xbee.print(' '); 
    //}
  }
  
  //xbee.print(c++);
  //delay(1000);
}

