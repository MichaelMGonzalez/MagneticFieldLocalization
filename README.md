# MagneticFieldLocalization
The itensities of an indoor magnetic field may be used to aid localization of a mobile robot. This repository contains all the required files for an experiment to map and localize this field.

| Task | Status | Priority |Comments | Next Steps
| ---  | ---    | ---      | ---     | ---
| **3D CAD**
| 3D Phone Model | In-Progress | Medium | Test Model Printed | Re-print larger model
| 3D IR Wheel Encoder Model | In-Progress | Medium | Test Model Printed | Re-print larger model
| ---
| **Circuit**
| Make Board       | Auto-Routed | HIGHEST | Waiting on Phone Model Test/ Schematic Verification | Order PCB
| Make Schemematic | Made Encoder| HIGHEST | Symbol/Phone Model Symbol/Voltage Divider | . | Label Resistor Values/Verify
| ---
| **Arduino Code**
| Program Wheel Encoder | General Logic Drafted | Medium | All of it is contained within an ino file | Transform into class
| PID Controller      | Drafted classes | Low | . | Write test cases
| Serial Communicator | External API's tested | Medium | Shared Message Framework created | Commit to GitHub/Draft module
| ---
| **Android Code**
| GUI | Not Started | Medium| . | Draft out a user flow chart
| Magnometer Sensor API | Not Started| High | . | Read through API's
| Serial Communicator | External API's tested | High | Shared Message Framework created | Commit to GitHub/Draft module
| Localizer | Not Started | Very High | Depends on most everything else before we can begin | Finish everything else
| Network Module | Not Started | Medium/Low | It would be really nice to have some way to control the robot from a computer | ?
| ---
| **Python Data Viewers**
| Histogram Plotter | Made Script to plot velocities | Low | . | Generalize?
| Scatter Plot | Made Script to plot sensor voltages | Low | . | Generalize?
| ---
