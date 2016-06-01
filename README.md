# MagneticFieldLocalization
The itensities of an indoor magnetic field may be used to aid localization of a mobile robot. This repository contains all the required files for an experiment to map and localize this field.

| Task | Status | Priority |Comments | Next Steps
| ---  | ---    | ---      | ---     | ---
| **3D CAD**
| 3D Phone Model | In-Progress | Medium | Test Model Printed | Re-print larger model
| 3D IR Wheel Encoder Model | In-Progress | Medium | Test Model Printed | Re-print larger model
| ---
| **Circuit**
| Make Board       | ORDERED     | HIGHEST | Waiting on Phone Model Test/ Schematic Verification | Solder
| Make Schemematic | Made Encoder| HIGHEST | Symbol/Phone Model Symbol/Voltage Divider | . | Label Resistor Values/Verify
| ---
| **Arduino Code**
| Main ino program    | Started | High | . | Draft
| Program Wheel Encoder | General Logic Drafted | Medium | Started to write header file| Test on hardware
| Differential Drive  | Classes made | Medium | Started to write header file| Test on hardware
| PID Controller      | Classes made | Low | . | Test on hardware
| Serial Communicator | Generation framework made | Medium | Shared Message Framework created | Lower baud rate
| ---
| **Android Code**
| GUI | Using sample code | Medium | . | Commit/Draft user flow chart
| Magnometer Sensor API | Not Started | High | . | Read through API's
| Serial Communicator | External API's tested | High | Shared Message Framework created | Lower baud rate
| Localizer | Not Started | Very High | Depends on most everything else before we can begin | Finish everything else
| Network Module | Not Started | Medium/Low | It would be really nice to have some way to control the robot from a computer | ?
| ---
| **Python Data Viewers**
| Histogram Plotter | Made Script to plot velocities | Low | . | Generalize?
| Scatter Plot | Made Script to plot sensor voltages | Low | . | Generalize?
| ---
