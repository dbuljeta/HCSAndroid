int ledPin = 4;
char received;
int x;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(57600);
  pinMode(ledPin, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available() > 0){
    x = Serial.read();
    Serial.print("PRIMIO SAM: ");
    Serial.println(x);
    if (x == 1){
      digitalWrite(ledPin, HIGH);
      Serial.println("Upalio sam ledicu\n");
      delay(60000);
    }
    else{
      digitalWrite(ledPin, LOW);
    }
  }
  else{
    digitalWrite(ledPin, LOW);
  }
  
}
