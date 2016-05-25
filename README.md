# MagneticFieldLocalization
The itensities of an indoor magnetic field may be used to aid localization of a mobile robot. This repository contains all the required files for an experiment to map and localize this field.

| Task | Status | Priority |Comments | Next Steps
| ---  | ---    | ---      | ---     | ---
| **3D CAD**
| 3D Phone Model | In-Progress | Medium | Test Model Printed | Re-print larger model
| 3D IR Wheel Encoder Model | Medium | In-Progress | Test Model Printed | Re-print larger model
| ---
| **Circuit**
| Make Board       | Auto-Routed | HIGHEST | Waiting on Phone Model Test/ Schematic Verification | Order PCB
| Make Schemematic | Made Encoder| HIGHEST | Symbol/Phone Model Symbol/Voltage Divider | . | Label Resistor Values/Verify
| ---
| **Arduino Code**
| Program Wheel Encoder | General Logic Drafted | Medium | All of it is contained within an ino file | Transform into class
| PID Controller      | Not Started | Low | . | Start!
| Serial Communicator | External API's tested | Medium | Shared Message Framework created | Commit to GitHub/Draft module
| ---
| **Android Code**
| GUI | Not Started | Medium| . | Draft out a user flow chart
| Magnometer Sensor API | Not Started| High | . | Read through API's
| Serial Communicator | External API's tested | High | Shared Message Framework created | Commit to GitHub/Draft module
| ---
| **Python Data Viewers**
| Histogram Plotter | Made Script to plot velocities | Low | . | Generalize?
| Scatter Plot | Made Script to plot sensor voltages | Low | . | Generalize?
| ---
