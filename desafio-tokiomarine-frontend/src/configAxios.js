import axios from 'axios'

const baseURL = 'http://localhost:8080'

const configAxios = axios.create({
  baseURL,
})
const requestHandler = request => {
  return request
}
const responseHandler = response => {
  return response
}
const errorHandler = error => {
  throw error
}
configAxios.interceptors.request.use(
  request => {
    return requestHandler(request)
  },
  error => errorHandler(error),
)
configAxios.interceptors.response.use(
  response => {
    return responseHandler(response)
  },
  error => {
    errorHandler(error)
  },
)
export default configAxios
