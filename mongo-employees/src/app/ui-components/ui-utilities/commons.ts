import { SelectItem } from "primeng/primeng";

export class Commons {

   fillSelectItem(data: Array<any>, labelField: string, valueField: string, items: Array<SelectItem>) {
        console.log('start fill-select-item method');
        if (data != null) {

            if (items.length > 0) {
                items = new Array<SelectItem>();
            }
            items.push({ label: 'Regions', value: '' })
            data.forEach((element) => {
                items.push({ label: element[labelField], value: element[valueField] })
            })
        } else {
            console.error('The data is empty');
        }
        console.log('end fill-select-item method');
    }

 
}