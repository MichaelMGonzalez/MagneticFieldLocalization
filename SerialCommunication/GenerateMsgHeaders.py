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
    before_text = file_type["before_def"]
    after_text  = file_type["after_def"]
    def_text    = file_type["def"]
    end_def     = file_type["end_def"]
    # Open the new file to write in
    new_file = open(new_name, 'w')
    nl = "\n"
    new_file.write(before_text + nl + nl)
    # Write the definitions to the file
    for m in json_obj["msgs"]:
        new_file.write( def_text + " " + m["name"] + " " + str(m["value"]))
        new_file.write( end_def + nl )
    new_file.write(nl + after_text)
    new_file.close()