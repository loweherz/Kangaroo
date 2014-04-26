#include <SoftwareSerial.h>

SoftwareSerial xbee(10, 9);
SoftwareSerial ext(3, 2);

unsigned long time;
String str;
boolean fileIsOpen=false;

void setup(){  
  Serial.begin(19200);
  
  xbee.begin(19200);
  ext.begin(4800);
  delay(10);    
}

void loop(){        
  if(ext.available()>0) {
    time = millis();
    delay(1);
    //Serial.print((char)ext.read());
    //IF CONNECTION IS 7E1 USE THE FOLLOW ROW
    char lettura = (char) (ext.read() & 0x7F);
    Serial.print(lettura);
    Serial.flush();    
  } 
}
