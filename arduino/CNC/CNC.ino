#include <SoftwareSerial.h>

SoftwareSerial ext(2,3);

unsigned long time = 50;

void setup() {                
  ext.begin(4800);
  Serial.begin(9600);
}

void loop(){  
  if(time - millis() < 50){
    delay(100);
    printProgram1();  
    //time=millis();
  }
  
  if(ext.available()){
    char lettura = (char) ext.read();
    Serial.print(lettura);
  }
}

// the loop routine runs over and over again forever:
void printProgram1() {  
  ext.println("%");
  ext.println(":2130(DIS 0BB311  ARDUINO)");
  ext.println("(SPESSORI DI 40 RETTIFICATI DA CONTROLLARE CON OROLOGIO)");
  ext.println("N10T1M6(CENTRINO)");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500"); 
 ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20T2M6(PUNTA 3.5)");
 ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
 ext.println("N20Z500");
  ext.println("N32T3M6(PUNTA 7.5)");
  ext.println("N20Z500");
 ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
 ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
 ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
 ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
 ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500"); 
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.println("N20Z500");
  ext.println("N30S2000");
  ext.println("N20Z500");
  ext.print("%"); 
}
