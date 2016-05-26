#define SENSOR_PIN A0
#define LED_PIN 13
#define SENSOR_DETECTION_THRESHHOLD 1005
#define SENSOR_FALLEN_THRESHHOLD 970
#define ENCODER_COUNT  5
int last_scan = 0;
int pegs_seen = 0;
long rotation_period = 0;
long last_rising_edge = 0;
bool waiting_for_edge = true;
void setup() {
  // put your setup code here, to run once:
  pinMode(LED_PIN, OUTPUT);
  Serial.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  int sensor_reading = analogRead(SENSOR_PIN);
  //log_data(sensor_reading);
  catch_rising_edge(sensor_reading);
}

void catch_rising_edge(int sensor_reading) {
  
  if( sensor_reading > last_scan && sensor_reading > SENSOR_DETECTION_THRESHHOLD && waiting_for_edge ) {
    long time_read = micros();
    //Serial.println("Rising edge detected!");
    //Serial.println( time_read - last_rising_edge );
    last_rising_edge = time_read;
    waiting_for_edge = false;   
    pegs_seen = (pegs_seen+1)%ENCODER_COUNT;
    //Serial.println(pegs_seen);
    if( pegs_seen == 0 ) {
      Serial.println( time_read - rotation_period );
      rotation_period = time_read;
    }
    
  }
  else if( sensor_reading > SENSOR_DETECTION_THRESHHOLD )
    digitalWrite(LED_PIN, HIGH);
  else if( sensor_reading < SENSOR_FALLEN_THRESHHOLD ) {
    waiting_for_edge = true;
    digitalWrite(LED_PIN, LOW);
  } 
  last_scan = sensor_reading;
}

void log_data(int sensor_reading) { 
  Serial.print( micros() );
  Serial.print( ", " );
  Serial.println( sensor_reading );
}
