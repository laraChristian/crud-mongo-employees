import { FormGroup } from "@angular/forms";

export interface Mapper {

    mapFormControlsToRequest(request: any, form: FormGroup, fieldsToMap: Array<string>): void;

    mapRequestToFormControls(request: any, form: FormGroup, fieldsToMap: Array<string>): void;
}
