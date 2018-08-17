import { Mapper } from "../../mappers/mapper";
import { FormGroup, FormBuilder, FormControl, Validators } from "@angular/forms";
import { LocationRequest } from "../../domain/locationRequest";
import { Commons } from "../ui-utilities/commons";
import { SelectItem } from "primeng/primeng";
import { Locations } from "../../entities/locations";

export class LocationsModel extends Commons implements Mapper {

    request: LocationRequest;
    locationForm: FormGroup;
    countryItems: Array<SelectItem>;
    //Datatable parameters
    locations: Array<Locations>;
    locationColums: any[];
    loading: boolean;
    //Datatable parameters
    disableDelete: boolean;


    constructor(private _formBuilder: FormBuilder) {
        super();
        this.countryItems = new Array<SelectItem>();
        this.initForm();
        this.clean();
        this.initColumns();
    }

    private initForm(): void {
        this.locationForm = this._formBuilder.group({
            'id': new FormControl('', Validators.nullValidator),
            'streetAddress': new FormControl('', Validators.required),
            'postalCode': new FormControl('', Validators.required),
            'city': new FormControl('', Validators.required),
            'stateProvince': new FormControl('', Validators.required),
            'countryId': new FormControl('', Validators.required)
        })
    }

    private initColumns() {
        this.locations = new Array<Locations>();
        this.locationColums = [{ field: 'streetAddress', header: 'Address' }, { field: 'postalCode', header: 'Postal Code' },
        { field: 'city', header: 'City' }, { field: 'stateProvince', header: 'State' }, { field: 'countryName', header: 'Country' }];
    }

    clean(): void {
        this.request = new LocationRequest();
        this.locationForm.reset();
        this.disableDelete = true;
    }

    mapFormControlsToRequest(request: any, form: FormGroup, fieldsToMap: Array<string>): void {
        console.info('start -- map-form-to-request method');
        fieldsToMap.forEach((field) => {
            request[field] = form.controls[field].value;
        });
        console.info('end -- map-form-to-request method');
    }

    mapRequestToFormControls(request: any, form: FormGroup, fieldsToMap: Array<string>): void {
        console.info('start -- map-form-to-request method');
        fieldsToMap.forEach((field) => {
            form.controls[field].setValue(request[field]);
        });
        console.info('end -- map-form-to-request method');
    }
}