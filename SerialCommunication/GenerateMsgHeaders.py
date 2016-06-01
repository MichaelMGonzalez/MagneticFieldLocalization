import json
import sys
# Open Header info
file_path = "SerialMsgHeader.json"
if len(sys.argv) > 1: file_path = sys.argv[1]


f = open(file_path)
json_obj = json.load(f)
file_name = file_path.split(".json")[0]
for file_type in json_obj["file_types"]: 
    # Load the format settings
    new_name    = file_name+"."+file_type["type"]
    if "name" in file_type: new_name = file_type["name"] + "." + file_type["type"]
    before_text = file_type["before_def"]
    after_text  = file_type["after_def"]
    def_text    = file_type["def"]
    setup_text  = file_type["setup_def"]
    end_def     = file_type["end_def"]
    assignment  = file_type["assignment"]
    comment     = file_type["comment"]
    

    if "dir" in file_type: new_name = file_type["dir"] + new_name
    # Open the new file to write in
    new_file = open(new_name, 'w')
    nl = "\n"
    if not ("ignore_def" in file_type):
        new_file.write(before_text + nl + nl)
    if comment != "NA":
        new_file.write(comment + "Setup Constants \n\n")
    for m in json_obj["setup"]:
        new_file.write( setup_text + " " + m["name"] + assignment)
	if not ("ignore_def" in file_type):
            new_file.write( str(m["value"]) + end_def + nl )
	else: new_file.write(  end_def + nl )
    # Write the definitions to the file
    keys_map = {}
    if comment != "NA":
        new_file.write(comment + "Message Constants \n\n")
    for m in json_obj["msgs"]:
        keys_map[m["name"]] = m["value"]
        new_file.write( def_text + " " + m["name"] + assignment)
	if not ("ignore_def" in file_type):
            new_file.write( str(m["value"]) + end_def + nl )
	else: new_file.write(  end_def + nl )

    if "map" in file_type:
        map_def = file_type["map"]
        keys = map_def["key_def"]
        keys += ", ".join([ '"'+k+'"' for k in keys_map.keys()]) 
        keys += map_def["end_def"]
        values = map_def["value_def"]
        values += ", ".join([ k for k in keys_map.keys()]) 
        values += map_def["end_def"]
        new_file.write(keys + nl)
        new_file.write(values + nl) 
        
    new_file.write(nl + after_text)
    new_file.close()
