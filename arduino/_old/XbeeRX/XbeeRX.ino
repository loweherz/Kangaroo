#include <SoftwareSerial.h>

SoftwareSerial xbee(10, 9);

unsigned long time;
String tmp;

void setup(){  
  Serial.begin(9600);  
        
  xbee.begin(19200); 
  delay(10);
}

void loop() {
  if(xbee.available()) {    
    time = millis();
    char lettura=(char)xbee.read();
    Serial.print(lettura);    
  }
}

