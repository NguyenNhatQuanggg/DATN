import swaggerJsdoc from 'swagger-jsdoc'
import swaggerUi from 'swagger-ui-express'
import { SERVER_ROUTE } from './src/utils/constants.js'

const options = {
    definition: {
        openapi: '3.0.3',
        info: {
            title: 'Cosmetics API',
            version: '1.0.0',
            description: "API endpoints for a cosmetics ecommerce documented on swagger",
        },
        servers: [
            {
                url: SERVER_ROUTE,
            },
        ]
    },
    // apis: ['./src/routes/*.js'],
    apis: ['./swagger-content.yml'],
}
const swaggerSpec = swaggerJsdoc(options)
function swaggerDocs(app) {
    // Swagger Page
    app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec))
    // Documentation in JSON format
    app.get('/api-docs.json', (req, res) => {
        res.setHeader('Content-Type', 'application/json')
        res.send(swaggerSpec)
    })
}
export default swaggerDocs