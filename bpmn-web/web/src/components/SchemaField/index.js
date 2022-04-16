import ArrayFieldVue from "./ArrayField.vue"
import BooleanFieldVue from "./BooleanField.vue"
import IntegerFieldVue from "./IntegerField.vue"
import NumberFieldVue from "./NumberField.vue"
import ObjectFieldVue from "./ObjectField.vue"
import SchemaFieldVue from "./SchemaField.vue"
import StringFieldVue from "./StringField.vue"

const SchemaFields = {
    install(app) {
        app.component('ArrayField', ArrayFieldVue);
        app.component('BooleanField', BooleanFieldVue);
        app.component('IntegerField', IntegerFieldVue);
        app.component('NumberField', NumberFieldVue);
        app.component('ObjectField', ObjectFieldVue);
        app.component('SchemaField', SchemaFieldVue);
        app.component('StringField', StringFieldVue);
    }
}
export default SchemaFields