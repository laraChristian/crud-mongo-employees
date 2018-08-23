import { SelectItem } from "primeng/primeng";
import { empty } from "rxjs";

export class Commons {

    private regex = "^\\s+$";

    fillSelectItem(data: Array<any>, labelField: string, valueField: string, items: Array<SelectItem>, defaultItem: SelectItem) {
        console.log('start fill-select-item method');
        if (data != null) {

            if (items.length > 0) {
                items = new Array<SelectItem>();
            }
            items.push(defaultItem);
            data.forEach((element) => {
                items.push({ label: element[labelField], value: element[valueField] })
            })
        } else {
            console.error('The data is empty');
        }
        console.log('end fill-select-item method');
    }

    validateFields(properties: string[], object: any): boolean {
        for (let prop of properties) {
            if (object[prop] == void 0 || object[prop] == null || object[prop].trim() == "") {
                return false;
            }
        }
        return true;
    }

}