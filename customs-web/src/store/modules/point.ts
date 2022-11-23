import {ObjTy} from "~/common";
import {getPointsList} from "@/api/point";

const actions = {
  getPointsList({ commit }: ObjTy, data: ObjTy) {
    return new Promise((resolve, reject) => {
      getPointsList(data)
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
