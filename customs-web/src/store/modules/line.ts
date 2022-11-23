import {ObjTy} from "~/common";
import {getLinesList} from "@/api/line";

const actions = {
  getLinesList({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
      getLinesList(data)
        .then((res) => {
          if (res && res.code === 200) {
            const data = res.data
            if (!data) {
              return reject(res.message)
            }
            return resolve(data)
          } else {
            return reject(res)
          }
        })
        .catch((error) => {
          return reject(error)
        })
    })
  },
}

export default {
  namespaced: true,
  actions
}
