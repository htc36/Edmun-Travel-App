import axios from 'axios';
import activitySearchAPI from "./scripts/Activity/activitySearch";

const SERVER_URL = process.env.VUE_APP_SERVER_ADD;
console.log(SERVER_URL + "@@@");

const instance = axios.create({
    baseURL: SERVER_URL,
    withCredentials: true
});

export default {

    instance: instance,

    // Other
    login: (email, password) => instance.post('/login',
        {email: email, password: password}),

    logout: () => instance.get('/logout/'),

    // (C)reate

    register: (registerData) => instance.post('/profiles',
        registerData),

    createActivity: (profileId, data) => instance.post(
        '/profiles/' + profileId + '/activities', data),

    subscribeToActivity: (userId, activityId) => instance.post(
        '/profiles/' + userId + '/subscriptions/activities/' + activityId),

    createActivityResult: (userId, activityId, data) => instance.post(
        'profiles/' + userId + '/activities/' + activityId + '/result', data),
    // (R)ead

    getProfileRoles: () => instance.get('/profiles/role'),

    getHomeFeed: (profileId, offset, limit) => instance.get(
        '/feed/homefeed/' + profileId + "?offset=" + offset + "&limit="
        + limit),

    getActivityParticipants: (activityId, offset, limit) => instance.get(
        '/activities/' + activityId + '/members' + "?type=participant&offset="
        + offset + "&limit=" + limit),

    getActivityOrganisers: (activityId, offset, limit) => instance.get(
        '/activities/' + activityId + '/members' + "?type=organiser&offset="
        + offset + "&limit=" + limit),

    getActivityAccessors: (activityId, offset, limit) => instance.get(
        '/activities/' + activityId + '/members' + "?type=accessor&offset="
        + offset + "&limit=" + limit),

    getActivityFollowers: (activityId, offset, limit) => instance.get(
        '/activities/' + activityId + '/members' + "?type=follower&offset="
        + offset + "&limit=" + limit),

    getActivityCreators: (activityId, offset, limit) => instance.get(
        '/activities/' + activityId + '/members' + "?type=creator&offset="
        + offset + "&limit=" + limit),

    getActivityMembers: (activityId) => instance.get(
        '/activities/' + activityId + '/members'),

    getLoggedInProfile: () => instance.get('/profiles/user'),

    getProfileId: () => instance.get('/profiles/id'),

    getProfile: (id) => instance.get('/profiles/' + id),

    getProfileByEmailAsync: async (email) => instance.get('/profiles?email='+ email),

    getFirstName: () => instance.get('/profiles/firstname'),

    getActivities: (profileId) => instance.get(
        '/profiles/' + profileId + '/activities'),

    getActivity: (activityId) => instance.get('/activities/' + activityId),

    getProfileActivityTypes: () => instance.get('/profiles/activity-types'),

    getHashtagAutocomplete: (hashtag) => instance.get(
        '/hashtag/autocomplete?hashtag=' + hashtag),

    getAdminRole: (profileId) => instance.get('/admin/role/' + profileId),

    getActivitiesByHashtag: (hashtag) => instance.get(
        '/activities/hashtag/' + hashtag),

    getActivityCreatorId: (activityId) => instance.get(
        '/activities/' + activityId + '/creatorId'),

    getIsSubscribed: (userId, activityId) => instance.get(
        '/profiles/' + userId + '/subscriptions/activities/' + activityId),

    getActivityMemberCounts: (activityId) => instance.get(
        '/activities/' + activityId + '/membercount'),

    getActivityTypes: () => instance.get('/profiles/activity-types'),

    getUserActivityResults: (userId, activityId) => instance.get(
        '/profiles/' + userId + '/activities/' + activityId + '/result'),

    getActivityMetrics: (profileId, activityId) => instance.get(
        '/profiles/' + profileId + '/activities/' + activityId + '/metrics'),


    getProfileEmails: (profileId) => instance.get('/profiles/' + profileId + '/emails'),

    getActivitiesBySearch: (search_query=undefined,
                            types = [],
                            types_method_and = true,
                            hashtags = [],
                            hashtags_method_and = true,
                            activity_mode_filter="all",
                            start_date=undefined,
                            end_date=undefined,
                            pagination_offset=0,
                            pagination_limit=10) =>
        {return activitySearchAPI.searchActivities(instance,
        search_query, types, types_method_and, hashtags, hashtags_method_and,
        activity_mode_filter, start_date, end_date, pagination_offset, pagination_limit)},

    getActivityCountBySearch: (search_query=undefined,
                               types = [],
                               types_method_and = true,
                               hashtags = [],
                               hashtags_method_and = true,
                               activity_mode_filter="all",
                               start_date=undefined,
                               end_date=undefined) =>
        {return activitySearchAPI.searchActivitiesPageCount(instance,
        search_query, types, types_method_and, hashtags, hashtags_method_and,
        activity_mode_filter, start_date, end_date)},

    // (U)pdate
    updateForId: (id, name) => instance.put('students/' + id, {name}),

    updateProfile: (profileId, updateData) => instance.put(
        '/profiles/' + profileId,
        updateData),

    updateActivityTypes: (profileId, data) => instance.put(
        '/profiles/' + profileId + '/activity-types', data),

    updateProfileEmails: (profileId, data) => instance.put(
        '/profiles/' + profileId + '/emails', data),

    updatePassword: (profileId, data) => instance.put(
        '/profiles/' + profileId + '/password', data),

    updateProfileLocation: (profileId, data) => instance.put(
        '/profiles/' + profileId + '/location', data),

    updateActivity: (profileId, activityId, data) => instance.put(
        '/profiles/' + profileId + '/activities/' + activityId, data),

    updateAdminRights: (profileId, data) => instance.put(
        '/admin/profiles/' + profileId + '/role', data),

    updatePasswordWithAdmin: (profileId, data) => instance.put(
        '/admin/profiles/' + profileId + '/password', data),

    updateRole: (profileId, activityId, data) => instance.put(
        '/profiles/' + profileId + '/activities/' + activityId + '/subscriber',
        data),

    updateActivityVisibility: (profileId, activityId, data) => instance.put(
        '/profiles/' + profileId + '/activities/' + activityId + '/visibility',
        data),

    updateActivityResult: (profileId, activityId, resultId,
        data) => instance.put(
        "/profiles/" + profileId + '/activities/' + activityId + '/result/'
        + resultId, data),
    // (D)elete

    removeForId: (id) => instance.delete('students/' + id),

    deleteActivity: (profileId, activityId) => instance.delete(
        '/profiles/' + profileId + '/activities/' + activityId),

    removeRole: (profileId, activityId, data) => instance.delete(
        '/profiles/' + profileId + '/activities/' + activityId + '/subscriber',
        {data}),

    removeLocation: (profileId) => instance.delete(
        '/profiles/' + profileId + '/location'),

    unsubscribeToActivity: (profileId, activityId) => instance.delete(
        '/profiles/' + profileId + '/subscriptions/activities/' + activityId),

    getAllActivityResultsByProfileId: (profileId, activityId, metricId) => instance.get('/activities/' + activityId + '/result/' + metricId + '/' + profileId),

    getAllActivityResultsByMetricId: (activityId, metricId) => instance.get('/activities/' + activityId + '/result/' + metricId),

    deleteActivityResult: (profileId, activityId, resultId) => instance.delete(
        "/profiles/" + profileId + '/activities/' + activityId + '/result/'
        + resultId)

}