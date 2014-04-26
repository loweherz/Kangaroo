#include <SoftwareSerial.h>

SoftwareSerial ext(3, 2);

int checkEnd = 0;

void setup(){  
  Serial.begin(19200);
  Serial.println("Arduino is connect..");
  ext.begin(4800);
  delay(10);    
}

void loop(){        
  if(ext.available()>0){
    //delay(1);
    //Serial.print((char)ext.read());
    //IF CONNECTION IS 7E1 USE THE FOLLOW ROW
    char lettura = (char) (ext.read());// & 0x7F);
    
    Serial.print(lettura);
    if(lettura == '%') checkEnd++;
    //Serial.flush(); 
    //delay(10);   
  }
 
  if(checkEnd>=2){
    Serial.print("C");
    checkEnd = 0;
  }  
  
  //DEVO INVIARE A 4800-7E1
  if(Serial.available()>0){            
    char lettura = (char) (Serial.read());    
    ext.print(lettura);    
    ext.flush();
    Serial.flush();    
  }

  //Serial.print(c++); delay(1000);  
}
