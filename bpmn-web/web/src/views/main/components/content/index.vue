<template>
    <keep-alive :include="cachedPages">
        <router-view :key="key"></router-view>
    </keep-alive>
</template>

<script>
import { mapWritableState } from "pinia";
import { useTagStore } from "@/stores/tag";
import { defineComponent } from "vue";
export default defineComponent({
    setup() {
        const store = useTagStore();
        return {
            store,
        };
    },
    computed: {
        ...mapWritableState(useTagStore, ["cachedPages"]),
        key() {
            return this.$route.fullPath
        }
    },
    watch: {
        $route() {
            this.addPage()
        }
    },
    methods: {
        addPage(router) {
            const { meta, name } = router
            if (name) {
                const cache = meta.cache === undefined || meta.cache
                const tab = meta.tab === undefined || meta.tab
                if (tab && cache) {
                    this.store.addPage(this.$route)
                } else if (cache) {
                    this.store.addCachedPage(this.$route)
                } else if (tab) {
                    this.store.addVisitedPage(this.$route)
                }
            }
        }
    },
});
</script>
