import { RegionRequest } from "../../domain/regionRequest";
import { Region } from "../../entities/regions";
import { FormGroup, FormBuilder, FormControl, Validators } from "../../../../node_modules/@angular/forms";

export class RegionModel {

    regionForm: FormGroup;
    request: RegionRequest;
    regions: Array<Region>;

    constructor(private _formGroup: FormBuilder) {
        this.initForm();
        this.clean();
        this.regions = Array<Region>();
    }

    clean(): void {
        this.request = new RegionRequest();
        this.regionForm.reset();
    }

    initForm() {
        this.regionForm = this._formGroup.group({ 'name': new FormControl('', Validators.required) });
    }
}